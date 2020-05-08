package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Comment;
import com.dgut.liukc.trainingsystem.javaBean.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {

    List<Comment> searchCommentsByArticleId(@Param("articleId")int articleId);

    void insertComment(@Param("comment") Comment comment);

    void insertMessage(@Param("message")Message message);

    List<Message> searchMessagesByEmpId(@Param("receiveId")int receiverId);

    void readMessage(@Param("id")int id);
}
