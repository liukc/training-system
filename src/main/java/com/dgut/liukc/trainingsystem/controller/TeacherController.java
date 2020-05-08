package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.javaBean.RespEmployee;
import com.dgut.liukc.trainingsystem.service.ArticleService;
import com.dgut.liukc.trainingsystem.service.EmployeeService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/getAllEmployees")
    public Detail searchAllEmployee(@RequestHeader("token") String token) {
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type != null && type.equals("admin")) {
            List<RespEmployee> employees = teacherService.searchAllEmployees();
            detail.getMap().put("employees", employees);
        }
        return detail;
    }

    @PostMapping("/deleteEmployee")
    public Detail deleteEmployeeById(@RequestBody Map map, @RequestHeader("token") String token) {
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            teacherService.deleteEmployeeById((Integer) map.get("id"));
        }
        return detail;
    }

    @PostMapping("/changeEmployeeType")
    public Detail changeEmployeeType(@RequestBody RespEmployee respEmployee, @RequestHeader("token") String token) {
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            teacherService.updateEmployeeType(respEmployee);
        }
        return detail;
    }

    @PostMapping("/addNewEmployee")
    public Detail addNewEmployee(@RequestBody RespEmployee respEmployee, @RequestHeader("token") String token){
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            detail.getMap().put("employee", teacherService.addNewEmployee(respEmployee));
        }
        return detail;
    }
}
