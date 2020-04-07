package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.EmployeeDao;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements EmployeeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public String getTeacherNameById(Integer id) {
        return employeeDao.getTeacherNameById(id);
    }
}
