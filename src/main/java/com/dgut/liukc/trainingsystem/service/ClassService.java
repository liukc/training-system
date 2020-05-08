package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Class;
import com.dgut.liukc.trainingsystem.javaBean.RespEmployee;

import java.util.List;
import java.util.Map;

public interface ClassService {

    List<Class> searchClassAndEmpByTeacherId(Integer id);

    List<Class> searchOngoingClassesByTeacherId(Integer id);

    Class insertClass(Map map);

    List<Class> searchOngoingClasses();

    RespEmployee updateEmployeeClass(Map map);
}
