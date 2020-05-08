package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Course;
import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Score;
import com.dgut.liukc.trainingsystem.service.ScoreService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ScoreController {

    @Autowired
    private Detail detail;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/getScore")
    public Detail searchScoresByEmpId(@RequestHeader("token") String token){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            List<Score> scores = scoreService.searchScoresByEmpId(id);
            detail.getMap().put("scores", scores);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/getEmpScore")
    public Detail searchEmpScoresByEmpId(@RequestHeader("token") String token, @RequestParam("empId") int empId){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            List<Score> scores = scoreService.searchScoresByEmpId(empId);
            detail.getMap().put("scores", scores);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }
}
