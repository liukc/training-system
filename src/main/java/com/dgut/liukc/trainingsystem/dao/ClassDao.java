package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassDao {
    List<Class> searchClassByTeacherId(@Param("teacherId") int id);

    List<Class> searchOngoingClassesByTeacherId(@Param("teacherId") int id);
}
