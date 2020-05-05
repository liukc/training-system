package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {

    Course insertCourse(Map map);

    List<Course> searchCoursesByEmpId(int empId);
}
