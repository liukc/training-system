package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Data
public class Article {

    private Integer id;
    private String title;
    private String content;
    private String description;
    private Date createTime;
    private String coverImg;
    private int hot;
    private Employee employee;
    private ArticleType articleType;
    private List<Label> labels;

    public Article(){
        this.labels = new ArrayList<>();
    }
}
