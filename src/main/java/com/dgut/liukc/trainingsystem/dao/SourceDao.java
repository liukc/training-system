package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Source;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SourceDao {

    Integer getSourceIdByMD5(@Param("md5") String md5);

    void insertSource(@Param("source")Source source);

    List<Source> selectSourcesByType(@Param("type") String type);
}
