package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.service.ArticleService;
import com.dgut.liukc.trainingsystem.service.EmployeeService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class TeacherController {

    @Autowired
    private Detail detail;

    @Autowired
    private EmployeeService teacherService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getTeacherName")
    public Detail getTeacherNameById(@RequestParam("teacherId") Integer id, @RequestHeader("token") String token) {
        detail.clear();
        Integer empId = (Integer) redisTemplate.opsForValue().get(token);
        if (empId == null) {
            detail.setStatus(4008);
        } else {
            detail.setStatus(2000);
            String name = teacherService.getTeacherNameById(id);
            detail.getMap().put("name", name);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }
}
