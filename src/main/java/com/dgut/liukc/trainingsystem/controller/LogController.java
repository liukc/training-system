package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.EmpJournal;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import com.dgut.liukc.trainingsystem.service.ArticleService;
import com.dgut.liukc.trainingsystem.utils.AuthenticationTool;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章，日志上传
 */
@CrossOrigin
@RestController
public class LogController {

    @Autowired
    private Detail detail;

    @Autowired
    @Qualifier("LogServiceImpl")
    private ArticleService articleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/getPersonalJournalDate")
    public Detail initPersonalJournal(@RequestHeader("token") String token){
        clearDetail();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            detail.getMap().put("logs", articleService.getLogs(id));
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/getLog")
    public Detail selectLogById(@RequestParam("id") Integer id, @RequestHeader("token") String token){
        clearDetail();
        Integer empId = (Integer) redisTemplate.opsForValue().get(token);
        if (empId != null) {
            detail.getMap().put("log",articleService.selectLogById(id));
        } else {
            detail.setStatus(4008);
        }
        return detail;
    }


    @PostMapping("/editLog")
    public Detail insertPersonalLog(@RequestBody EmpJournal empJournal, @RequestHeader("token") String token) {
        clearDetail();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            empJournal.setEmpId(id);
            detail.getMap().put("log",articleService.editPersonalLog(empJournal));
        } else {
            detail.setStatus(4008);
        }
        return detail;
    }

    @PutMapping("/changeTrainingPeriod")
    public Detail changeTrainPeriod(@RequestHeader("token") String token, @RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) {
        clearDetail();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        int legalDate = articleService.isLegalBeginDate(beginDate);
        if (id != null && legalDate == 1) {
            List logs = articleService.changeTrainingPeriod(id, beginDate, endDate);
            if (logs == null) {
                detail.setStatus(5000);
            } else {
                detail.getMap().put("logs", logs);
                detail.setStatus(2000);
            }
        } else if (id == null) {
            detail.setStatus(4008);
        } else {
            detail.setStatus(4012);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    private void clearDetail(){
        detail.setStatus(2000);
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        detail.getMap().clear();
    }
}
