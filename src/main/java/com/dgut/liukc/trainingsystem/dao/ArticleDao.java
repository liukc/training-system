package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.EmpJournal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleDao {

    Integer isLogExist(@Param("id")int id);

    int insertLog(@Param("log")EmpJournal log);

    int updateLog(@Param("log")EmpJournal log);

    int insertLogList(@Param("logs")List logs);

    List selectLogsByEmpId(@Param("empId") int empId);

    EmpJournal selectLogById(@Param("id") int id);

    int deleteLogsByCreateTime(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("empId") int empId);
}
