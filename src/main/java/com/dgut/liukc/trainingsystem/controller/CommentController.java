package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Article;
import com.dgut.liukc.trainingsystem.javaBean.Comment;
import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.javaBean.Message;
import com.dgut.liukc.trainingsystem.service.CommentService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CommentController {
    @Autowired
    private Detail detail;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommentService commentService;

    @GetMapping("/getAllComments")
    public Detail searchCommentsByArticleId(@RequestParam("articleId") Integer articleId){
        detail.clear();
        List<Comment> comments = commentService.searchCommentsByArticleId(articleId);
        detail.getMap().put("comments", comments);
        return detail;
    }

    @PostMapping("/addComment")
    public Detail insertComment(@RequestBody Map map, @RequestHeader("token")String token){
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            map.put("empId", id);
            Comment comment = commentService.insertComment(map);
            detail.getMap().put("comment", comment);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/getAllMessage")
    public Detail searchMessages(@RequestHeader("token")String token){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            List<Message> messages = commentService.searchMessagesByEmpId(id);
            detail.getMap().put("messages", messages);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @PostMapping("/readMessage")
    public Detail readMessage(@RequestBody Map map, @RequestHeader("token")String token){
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            Article article = commentService.readMessage(map);
            detail.getMap().put("article", article);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }
}
