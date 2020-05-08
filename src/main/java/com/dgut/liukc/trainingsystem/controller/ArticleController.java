package com.dgut.liukc.trainingsystem.controller;

import com.dgut.liukc.trainingsystem.javaBean.Article;
import com.dgut.liukc.trainingsystem.javaBean.Class;
import com.dgut.liukc.trainingsystem.javaBean.Detail;
import com.dgut.liukc.trainingsystem.service.ArticleService;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    private Detail detail;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/publishArticle")
    public Detail insertArticle(@RequestBody Map map, @RequestHeader("token") String token) {
        detail.clear();
        Integer id = (Integer) redisTemplate.opsForValue().get(token);
        if (id != null) {
            map.put("empId", id);
            Article article = articleService.insertArticle(map);
            detail.getMap().put("article", article);
        } else {
            detail.setStatus(4008);
        }
        detail.setMessage(PropertiesOP.getMessageByStatus(detail.getStatus()));
        return detail;
    }

    @GetMapping("/getAllArticles")
    public Detail searchArticleByType(@RequestParam("type") int type) {
        detail.clear();
        List<Article> articles = articleService.searchArticlesByTypeId(type);
        detail.getMap().put("articles", articles);
        return detail;
    }

    @GetMapping("/getArticles")
    public Detail searchArticles(@RequestHeader("token") String token) {
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            detail.getMap().put("articles", articleService.searchArticles());
        }
        return detail;
    }

    @GetMapping("/getArticlesByHot")
    public Detail searchArticleByHot() {
        detail.clear();
        List<Article> articles = articleService.searchArticlesByHot();
        detail.getMap().put("articlesHot", articles);
        return detail;
    }

    @PostMapping("/readArticle")
    public Detail readArticle(@RequestBody Map map){
        detail.clear();
        Article article = articleService.searchArticleById((Integer) map.get("articleId"));
        detail.getMap().put("article", article);
        return detail;
    }

    @GetMapping("/searchArticle")
    public Detail searchArticleByTitle(@RequestParam("word")String word){
        detail.clear();
        List<Article> articles = articleService.searchArticlesByTitle(word);
        detail.getMap().put("articles", articles);
        return detail;
    }

    @PostMapping("/deleteArticle")
    public Detail deleteArticle(@RequestBody Map map, @RequestHeader("token") String token){
        detail.clear();
        String type = (String) redisTemplate.opsForValue().get(token);
        if (type.equals("admin")) {
            articleService.deleteArticleById((Integer) map.get("id"));
        }
        return detail;
    }
}
