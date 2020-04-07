package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.AuthenticationDao;
import com.dgut.liukc.trainingsystem.javaBean.EmpLoginInfo;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.service.AuthenticationService;
import com.dgut.liukc.trainingsystem.utils.MD5Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * @author liukc
 */
@Service("RegisterServiceImpl")
public class RegisterServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationDao authenticationDao;

    @Autowired
    private EmpLoginInfo empLoginInfo;
    @Autowired
    private Employee employee;

    @Override
    public int register(String email, String password, String type) {
        int status = 2000;
        if (authenticationDao.emailIsExist(email) != null) {
            status = 4009;
        } else {
            String newPassword;
            try {
                newPassword = MD5Encryption.encryptPassword(password);
            } catch (NoSuchAlgorithmException e) {
                logger.error("MD5 算法加密失败..." + e);
                status = 5000;
                return status;
            }

            int id = generateDefaultUser(type);
            if (id != 0) {
                empLoginInfo.setEmail(email);
                empLoginInfo.setEmpId(id);
                empLoginInfo.setPassword(newPassword);

                int result = authenticationDao.insertEmpLoginInfo(empLoginInfo);
                if (result == 0) {
                    logger.error("登录数据插入失败...");
                    status = 5000;
                }
            } else {
                logger.error("插入员工数据失败...");
                status = 5000;
            }
        }
        return status;
    }

    public int generateDefaultUser(String type) {
        String name = "自注册用户";
        String headIcon = "http://localhost:8081/defaultIcon.jpg";

        employee.setName(name);
        employee.setHeadIcon(headIcon);
        employee.setType(type);
        employee.setDescription("自注册用户");
        int result = authenticationDao.insertEmployee(employee);
        if (result != 0){
            result = employee.getId();
        }
        return result;
    }
}
