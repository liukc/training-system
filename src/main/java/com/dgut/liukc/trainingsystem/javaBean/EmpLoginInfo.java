package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmpLoginInfo {
    private int id;
    private String ip;
    private String email;
    private String password;
    private int mistakes;
    private int empId;
}
