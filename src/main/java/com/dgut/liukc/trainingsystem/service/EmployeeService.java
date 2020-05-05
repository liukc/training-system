package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Employee;

import java.util.List;

public interface EmployeeService {

    String getTeacherNameById(Integer id);

    List<Employee> searchEmployeesByTeacherId(Integer id);
}
