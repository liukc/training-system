package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScoreDao {

    void insertScore(@Param("score")Score score);

    List<Score> searchScoresByEmpId(@Param("empId")int empId);
}
