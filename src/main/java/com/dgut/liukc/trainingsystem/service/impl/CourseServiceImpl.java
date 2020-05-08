package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.CourseDao;
import com.dgut.liukc.trainingsystem.dao.ExamDao;
import com.dgut.liukc.trainingsystem.dao.SourceDao;
import com.dgut.liukc.trainingsystem.javaBean.Class;
import com.dgut.liukc.trainingsystem.javaBean.Course;
import com.dgut.liukc.trainingsystem.javaBean.Exam;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import com.dgut.liukc.trainingsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private ExamDao examDao;

    @Override
    public Course insertCourse(Map map) {
        Course course = new Course();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        entrySet.forEach(entry -> {
            switch (entry.getKey()) {
                case "examId":
                    Exam exam = new Exam();
                    exam.setId((Integer) entry.getValue());
                    course.setExam(exam);
                    break;
                case "name":
                    course.setName((String) entry.getValue());
                    break;
                case "classId":
                    if (entry.getValue() instanceof List) {
                        ((List) entry.getValue()).forEach(classId -> {
                            Class c = new Class();
                            c.setId((Integer) classId);
                            course.getClasses().add(c);
                        });
                    }
                    break;
                case "sourcesId":
                    if (entry.getValue() instanceof List) {
                        ((List) entry.getValue()).forEach(sourceId -> {
                            Source c = new Source();
                            c.setId((Integer) sourceId);
                            course.getSources().add(c);
                        });
                    }
                    break;
            }
        });
        courseDao.insertCourse(course);
        courseDao.insertCourseAndSource(course);
        courseDao.insertCourseAndClass(course);
        return course;
    }

    @Override
    public List<Course> searchCoursesByEmpId(int empId) {
        List<Course> courses = courseDao.searchCoursesByEmpId(empId);
        courses.forEach(course -> {
            if (course.getExam().getId() != null){
               course.setExam(examDao.searchExamById(course.getExam().getId()));
            }
            course.setSources(sourceDao.searchSourceByCourseId(course.getId()));
        });
        return courses;
    }

    @Override
    public List<Course> searchCoursesByClassId(int classId) {
        List<Course> courses = courseDao.searchCoursesByClassId(classId);
        courses.forEach(course -> {
            if (course.getExam().getId() != null){
                course.setExam(examDao.searchExamById(course.getExam().getId()));
            }
            course.setSources(sourceDao.searchSourceByCourseId(course.getId()));
        });
        return courses;
    }

    @Override
    public void deleteCourseById(int courseId) {
        courseDao.deleteCourseById(courseId);
    }
}
