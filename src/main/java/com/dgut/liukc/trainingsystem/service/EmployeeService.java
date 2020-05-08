package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.javaBean.RespEmployee;

import java.util.List;

public interface EmployeeService {

    String getTeacherNameById(Integer id);

    List<Employee> searchEmployeesByTeacherId(Integer id);

    List<RespEmployee> searchAllEmployees();

    void deleteEmployeeById(int id);

    void updateEmployeeType(RespEmployee respEmployee);

    RespEmployee addNewEmployee(RespEmployee respEmployee);
}
