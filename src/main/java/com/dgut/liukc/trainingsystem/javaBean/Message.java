package com.dgut.liukc.trainingsystem.javaBean;

import com.dgut.liukc.trainingsystem.javaBean.Comment;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class Message {

    private Integer id;
    private Date createTime;
    private String content;
    private Employee sender;
    private Employee receiver;
    private int isRead;
    private Comment comment;
}
