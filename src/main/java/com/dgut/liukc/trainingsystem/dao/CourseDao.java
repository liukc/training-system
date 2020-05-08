package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Course;
import com.dgut.liukc.trainingsystem.javaBean.Exam;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseDao {

    void insertCourse(@Param("course")Course course);

    void insertCourseAndSource(@Param("course")Course course);

    void insertCourseAndClass(@Param("course")Course course);

    List<Course> searchCoursesByEmpId(@Param("empId") int empId);

    List<Course> searchCoursesByClassId(@Param("classId") int classId);

    Course searchCourseByExamId(@Param("examId")int examId);

    void deleteCourseById(@Param("id") int id);
}
