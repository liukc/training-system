package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Article;
import com.dgut.liukc.trainingsystem.javaBean.Comment;
import com.dgut.liukc.trainingsystem.javaBean.Message;

import java.util.List;
import java.util.Map;

public interface CommentService {

    Comment insertComment(Map map);

    List<Comment> searchCommentsByArticleId(int articleId);

    List<Message> searchMessagesByEmpId(int receiverId);

    Article readMessage(Map map);
}
