package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Course;
import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Exam;
import com.dgut.liukc.trainingsystem.service.CourseService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CourseController {

    @Autowired
    private Detail detail;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/addCourse")
    public Detail insertCourse(@RequestBody Map map, @RequestHeader("token") String token) {
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            Course course = courseService.insertCourse(map);
            detail.getMap().put("course", course);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/getAllCourseByEmp")
    public Detail searchCourseByEmp(@RequestHeader("token") String token){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            List courses = courseService.searchCoursesByEmpId(id);
            detail.getMap().put("courses", courses);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/getAllCourseByClass")
    public Detail searchCourseByClass(@RequestHeader("token") String token, @RequestParam("classId")int classId){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            List courses = courseService.searchCoursesByClassId(classId);
            detail.getMap().put("courses", courses);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/deleteCourseById")
    public Detail deleteCourseById(@RequestHeader("token") String token, @RequestBody Map map){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            courseService.deleteCourseById((Integer) map.get("courseId"));
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }
}
