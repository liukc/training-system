package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import com.dgut.liukc.trainingsystem.service.SourceService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class SourceController {
    private Detail detail;

    private SourceService sourceService;

    private RedisTemplate redisTemplate;

    @Autowired
    public SourceController(Detail detail, SourceService sourceService, RedisTemplate redisTemplate) {
        this.detail = detail;
        this.sourceService = sourceService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/uploadSource")
    public Detail sourceUpload(@RequestParam("source") MultipartFile multipartFile, @RequestParam(value = "description", required = false) String desc, @RequestHeader("token") String token) {
        Integer empId = (Integer) redisTemplate.opsForValue().get(token);
        if (empId == null) {
            detail.setStatus(4008);
        } else if (multipartFile == null) {
            detail.setStatus(4011);
        } else {
            Source source = sourceService.sourceUpload(multipartFile, desc, empId);
            if (source == null) {
                detail.setStatus(5000);
            } else {
                detail.getMap().put("source", source);
            }
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }
}
