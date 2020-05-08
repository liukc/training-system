package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import com.dgut.liukc.trainingsystem.service.SourceService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
    public Detail sourceUpload(@RequestParam("source") MultipartFile multipartFile,
                               @RequestParam(value = "description", required = false) String desc,
                               @RequestHeader("token") String token) {
        detail.clear();
        Integer empId = (Integer) redisTemplate.opsForValue().get(token);
        if (empId == null) {
            detail.setStatus(4008);
        } else if (multipartFile == null) {
            detail.setStatus(4011);
        } else {
            Source source = sourceService.sourceUpload(multipartFile, desc, empId, 0);
            if (source == null) {
                detail.setStatus(5000);
            } else {
                detail.getMap().put("source", source);
            }
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/uploadSources")
    public Detail sourcesUpload(@RequestParam("files")MultipartFile[] files, @RequestHeader("token") String token){
        detail.clear();
        Integer empId = (Integer) redisTemplate.opsForValue().get(token);
        if (empId == null) {
            detail.setStatus(4008);
        } else if (files == null || files.length == 0) {
            detail.setStatus(4011);
        } else {
            List<Source> sources = sourceService.sourcesUpload(files,empId,1);
            detail.getMap().put("sources", sources);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/SourceList")
    public Detail selectSources(@RequestParam("type") String type) {
        detail.clear();
        detail.getMap().put("sources", sourceService.selectSources(type));
        return detail;
    }

    @PostMapping("/getSourceById")
    public Detail searchSourceById(@RequestBody Map map, @RequestHeader("token") String token){
        detail.clear();
        Object empId = redisTemplate.opsForValue().get(token);
        if (empId == null) {
            detail.setStatus(4008);
        } else {
            Source source = sourceService.searchSourceById((Integer) map.get("sourceId"));
            detail.getMap().put("source", source);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/getSourcesByHot")
    public Detail searchSourcesByHot(@RequestParam("type")String type){
        detail.clear();
        detail.getMap().put("sources", sourceService.searchSourcesByHot(type));
        return detail;
    }

    @PostMapping("/deleteSource")
    public Detail deleteSource(@RequestBody Map map, @RequestHeader("token") String token){
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            sourceService.deleteSourceById((Integer) map.get("id"));
        }
        return detail;
    }
}
