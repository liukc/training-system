package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.ClassDao;
import com.dgut.liukc.trainingsystem.javaBean.Class;
import com.dgut.liukc.trainingsystem.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassDao classDao;
    @Override
    public List<Class> searchClassAndEmpByTeacherId(Integer id) {
        return classDao.searchClassByTeacherId(id);
    }

    @Override
    public List<Class> searchOngoingClassesByTeacherId(Integer id) {
        return classDao.searchOngoingClassesByTeacherId(id);
    }
}
