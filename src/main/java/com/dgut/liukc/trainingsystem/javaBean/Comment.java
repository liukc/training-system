package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class Comment {
    private Integer id;
    private String content;
    private Date createTime;
    private int floor;
    private int like;
    private Employee employee;
    private Employee receiver;   // 被评论人
    private Article article;
}
