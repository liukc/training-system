package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class RespEmployee {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String department;
    private String type;
    private Class aClass;
}
