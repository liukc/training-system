package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
public class Class {
    private Integer id;
    private String name;
    private Date beginDate;
    private Date endDate;
    private int number;
    private Integer teacherId;
    private List<Employee> employees;
}
