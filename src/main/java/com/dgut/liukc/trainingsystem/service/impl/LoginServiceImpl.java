package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.AuthenticationDao;
import com.dgut.liukc.trainingsystem.javaBean.EmpLoginInfo;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.service.AuthenticationService;
import com.dgut.liukc.trainingsystem.utils.MD5Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author liukc
 */
@Service("LoginServiceImpl")
@Scope("prototype")
public class LoginServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationDao authenticationDao;

    @Autowired
    private EmpLoginInfo empLoginInfo;

    @Autowired
    private RedisTemplate redisTemplate;

    private int status;

    @Override
    public int checkLoginInfo(String account, String inputtedPassword) {
        status = 2000;
        // 检查是否为邮箱登录
        if (account.contains("@")) {
            // 检查邮箱是否存在
            Integer id = authenticationDao.emailIsExist(account);
            if (id == null) {
                status = 4001;
            } else {  // 检查密码是否正确
                recordLoginStatus(inputtedPassword, id, account);
            }
        } else {
            recordLoginStatus(inputtedPassword, Integer.valueOf(account), account);
        }
        return status;
    }

    @Override
    public void recordLoginStatus(String inputtedPassword, Integer empId, String account) {
        boolean isPassword = false;
        String password = authenticationDao.getPasswordByEmpId(empId);
        if (password == null || password.isEmpty()) {
            status = 4003;
            return;
        }
        try { // 判断密码是否正确
            isPassword = MD5Encryption.checkPassword(password, inputtedPassword);
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5 算法加密失败..." + e);
            status = 5000;
            return;
        }
        if (isPassword) {
            int level = authenticationDao.getDisableLevelByEmpId(empId);
            if (level == 2) { // 被管理员封禁
                status = 4005;
            } else if (level == 1) {
                authenticationDao.ableAccountByEmpId(empId);
            }
            redisTemplate.opsForHash().put("account", account, 0); // 把试错次数置为0
        } else { // 密码错误
            status = 4002;
            Integer wrongNum = (Integer) redisTemplate.opsForHash().get("account", account);
            if (wrongNum == null) {
                redisTemplate.opsForHash().put("account", account, 1);
            } else if (wrongNum == 5) { // 输入密码错误超过5次，禁用一天
                redisTemplate.opsForValue().set(account, "disableLevelOne");
                redisTemplate.expire(account, 1, TimeUnit.DAYS);
                authenticationDao.disableAccountByEmpId(empId, 1);
            } else {
                wrongNum++;
                redisTemplate.opsForHash().put("account", account, wrongNum);
            }
        }
    }

    @Override
    public Employee getEmployee(String account) {
        if (account.contains("@")) {
            int id = authenticationDao.emailIsExist(account);
            return authenticationDao.getEmployeeById(id);
        }
        return authenticationDao.getEmployeeById(Integer.valueOf(account));
    }
}
