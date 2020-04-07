package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.service.AuthenticationService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册
 *
 * @author liukc
 */
@CrossOrigin
@RestController
public class RegisterController {

    @Autowired
    private Detail detail;

    @Autowired
    @Qualifier("RegisterServiceImpl")
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public Detail employeeRegister(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam(value = "type", defaultValue = "新员工") String type) {
        int result = authenticationService.register(email, password, type);
        detail.setStatus(result);
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }
}
