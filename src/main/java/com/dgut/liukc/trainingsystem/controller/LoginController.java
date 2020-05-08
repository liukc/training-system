package com.dgut.liukc.trainingsystem.controller;


import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.service.AuthenticationService;
import com.dgut.liukc.trainingsystem.utils.MD5Encryption;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import com.dgut.liukc.trainingsystem.utils.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 员工登录
 *
 * @author liukc
 */
@CrossOrigin
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Detail detail;

    @Autowired
    @Qualifier("LoginServiceImpl")
    private AuthenticationService authenticationService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 接收和传递员工登录信息
     *
     * @param account  员工账号或邮箱
     * @param password 密码
     * @return detail：{status：message{401：邮箱不存在。402：密码错误, 403: 账号不存在, 405: 账号被禁用，请联系管理员}, map: 员工个人信息}
     */
    @PostMapping("/login")
    public Detail employeeLogin(@RequestParam("account") String account, @RequestParam("password") String password) {
        detail.getMap().clear();
        if (redisTemplate.opsForValue().get(account) != null) {
            detail.setStatus(4007);
            detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
            return detail;
        }
        int result = authenticationService.checkLoginInfo(account, password);
        detail.setStatus(result);
        detail.getMap().clear();
        if (result == 2000) {
            // 验证成功，doSomething
            String salt = UUIDGenerator.getUUID();
            Employee employee = authenticationService.getEmployee(account);
            String token;
            try {
                token = MD5Encryption.generateTokenByUserInfo(employee, salt);
            } catch (NoSuchAlgorithmException e) {
                logger.error("MD5 算法加密失败...", e);
                detail.setStatus(5000);
                detail.setMessage(PropertiesOP.getMessageByStatus(5000));
                return detail;
            }
            // 存储token4个小时
            redisTemplate.opsForValue().set(token, employee.getId(), 4, TimeUnit.HOURS);
            detail.getMap().put("token", token);
        } else if (result == 4002) {  // 密码错误
            int wrongNum = (int) redisTemplate.opsForHash().get("account", account);
            detail.setMessage(String.format(PropertiesOP.getMessageByStatus(detail.getStatus()), (5 - wrongNum)));
        } else { // 其他情况
            detail.setMessage(PropertiesOP.getMessageByStatus(result));
        }
        return detail;
    }

    /**
     * FIXME token 存储和生成有问题，需要修改
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/getEmpInfo")
    public Detail getEmpInfo(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("token");
        Integer empId = (Integer) redisTemplate.opsForValue().get(token);
        detail.getMap().clear();
        if (empId != null) { // 只要存在 token 就好，不验证 token 是否正确，虽然这样存在很大的安全隐患
            Employee employee = authenticationService.getEmployee(Integer.toString(empId));
            detail.getMap().put("employee", employee);
            detail.setStatus(2000);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/adminLogin")
    public Detail adminLogin(@RequestParam("account") String account, @RequestParam("password") String password) {
        detail.clear();
        if (redisTemplate.opsForValue().get(account) != null) {
            detail.setStatus(4007);
            detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
            return detail;
        }
        if ("admin@lkc.com".equals(account) && "admin".equals(password)){
            // 验证成功，doSomething
            String salt = UUIDGenerator.getUUID();
            Employee employee = new Employee();
            employee.setType("admin");
            String token;
            try {
                token = MD5Encryption.generateTokenByUserInfo(employee, salt);
            } catch (NoSuchAlgorithmException e) {
                logger.error("MD5 算法加密失败...", e);
                detail.setStatus(5000);
                detail.setMessage(PropertiesOP.getMessageByStatus(5000));
                return detail;
            }
            // 存储token4个小时
            redisTemplate.opsForValue().set(token, employee.getType(), 4, TimeUnit.HOURS);
            detail.getMap().put("token", token);
        }else {
            detail.setStatus(4002);
            detail.setMessage("密码错误");
        }
        return detail;
    }
}
