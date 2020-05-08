package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.dao.AuthenticationDao;
import com.dgut.liukc.trainingsystem.javaBean.EmpLoginInfo;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface AuthenticationService {
    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    default int register(String email, String password, String type) {
        return 2000;
    }

    default int checkLoginInfo(String account, String password) {
        return 200;
    }

    default void recordLoginStatus(String inputtedPassword, Integer empId, String account) {
    }

    default Employee getEmployee(String account) {
        return null;
    }
}
