package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Employee {
    private Integer id;
    private String name;
    private String department;
    private String headIcon;
    private String type;
    private String description;
    private Date beginDate;
    private Date endDate;
    private int teacherId;
}
