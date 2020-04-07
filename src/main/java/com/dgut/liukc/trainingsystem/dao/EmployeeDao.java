package com.dgut.liukc.trainingsystem.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface EmployeeDao {

    /**
     * getTeacherNameById
     * @param id 导师id
     * @return 导师姓名
     */
    public String getTeacherNameById(@Param("id") Integer id);

    int updateTrainingPeriod(@Param("id")int id, @Param("beginDate") Date beginDate, @Param("endDate")Date endDate);
}
