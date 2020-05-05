package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Exam;
import com.dgut.liukc.trainingsystem.javaBean.Score;
import com.dgut.liukc.trainingsystem.service.ExamService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class ExamController {

    @Autowired
    private Detail detail;
    @Autowired
    private ExamService examService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/addExam")
    public Detail insertExam(@RequestBody Map map, @RequestHeader("token") String token) {
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            Exam exam = examService.insertExam(map);
            detail.getMap().put("exam", exam);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/getExamById")
    public Detail searchExamById(@RequestParam("id")int id, @RequestHeader("token") String token){
        detail.clear();
        Integer empId = (Integer) redisTemplate.opsForValue().get(token);
        if (empId != null) {
            Exam exam = examService.searchExamById(id);
            detail.getMap().put("exam", exam);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/doExam")
    public Detail doExam(@RequestBody Exam exam, @RequestHeader("token") String token){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            Score score = examService.doExam(exam,id);
            detail.getMap().put("score", score);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }
}
