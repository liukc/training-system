package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.ClassDao;
import com.dgut.liukc.trainingsystem.dao.EmployeeDao;
import com.dgut.liukc.trainingsystem.javaBean.Class;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.javaBean.RespEmployee;
import com.dgut.liukc.trainingsystem.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {

    private Logger logger = LoggerFactory.getLogger(ClassServiceImpl.class);

    @Autowired
    private ClassDao classDao;

    @Autowired
    private EmployeeDao employeeDao;

    private String DATE_FORMAT = "yyyy-MM-dd";
    @Override
    public List<Class> searchClassAndEmpByTeacherId(Integer id) {
        return classDao.searchClassByTeacherId(id);
    }

    @Override
    public List<Class> searchOngoingClassesByTeacherId(Integer id) {
        return classDao.searchOngoingClassesByTeacherId(id);
    }

    @Override
    public Class insertClass(Map map) {
        Class newClass = new Class();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date bDate;
        Date eDate;
        try {
            bDate = dateFormat.parse((String) map.get("beginDate"));
            eDate = dateFormat.parse((String) map.get("endDate"));
        } catch (ParseException e) {
            logger.error("日期格式转换失败...", e);
            return null;
        }
        newClass.setName((String) map.get("name"));
        newClass.setBeginDate(bDate);
        newClass.setEndDate(eDate);
        classDao.insertClass(newClass);
        return newClass;
    }

    @Override
    public List<Class> searchOngoingClasses() {
        return classDao.searchOngoingClasses();
    }

    @Override
    public RespEmployee updateEmployeeClass(Map map) {
        // 1. 根据员工类型进行分配
        String type = (String) map.get("type");
        Class aclass = classDao.searchClassById((Integer) map.get("classId"));
        Integer empId = (Integer) map.get("employeeId");
        RespEmployee respEmployee = null;
        Employee employee=null;
        if ("导师".equals(type)){
            aclass.setTeacherId(empId);
            employeeDao.updateEmployeeTeacher(empId, aclass.getId());
        }else {
            employee = employeeDao.searchEmployeeById(empId);
            employee.setClassId(aclass.getId());
            employee.setTeacherId(aclass.getTeacherId());
            aclass.setNumber(aclass.getNumber()+1);
            employeeDao.updateEmployeeClass(employee);

            respEmployee = new RespEmployee();
            respEmployee.setId(employee.getId());
            respEmployee.setDepartment(employee.getDepartment());
            respEmployee.setName(employee.getName());
            respEmployee.setType(employee.getType());
            respEmployee.setAClass(aclass);
        }
        classDao.updateClass(aclass);

        return respEmployee;
    }
}
