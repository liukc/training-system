package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Class;

import java.util.List;

public interface ClassService {

    List<Class> searchClassAndEmpByTeacherId(Integer id);

    List<Class> searchOngoingClassesByTeacherId(Integer id);
}
