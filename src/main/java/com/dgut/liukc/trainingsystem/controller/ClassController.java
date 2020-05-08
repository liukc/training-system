package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Class;
import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.service.ClassService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ClassController {
    @Autowired
    private Detail detail;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ClassService classService;

    @GetMapping("/empLogs")
    public Detail getEmpsLogs(@RequestHeader("token") String token) {
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            List<Class> classes = classService.searchClassAndEmpByTeacherId(id);
            detail.getMap().put("classes", classes);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/getAllClasses")
    public Detail getAllClasses(@RequestHeader("token") String token){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            List<Class> classes = classService.searchOngoingClassesByTeacherId(id);
            detail.getMap().put("classes", classes);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/addClass")
    public Detail insertClass(@RequestBody Map map, @RequestHeader("token") String token){
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            detail.getMap().put("class",classService.insertClass(map));
        }
        return detail;
    }

    @GetMapping("/getAllOnGoingClasses")
    public Detail searchAllOnGoingClasses(@RequestHeader("token") String token){
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            detail.getMap().put("classes",classService.searchOngoingClasses());
        }
        return detail;
    }

    @PostMapping("/allocClass")
    public Detail updateEmployeeClass(@RequestBody Map map, @RequestHeader("token") String token){
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            detail.getMap().put("employee",classService.updateEmployeeClass(map));
        }
        return detail;
    }
}
