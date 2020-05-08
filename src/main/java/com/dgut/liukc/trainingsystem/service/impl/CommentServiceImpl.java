package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.ArticleDao;
import com.dgut.liukc.trainingsystem.dao.CommentDao;
import com.dgut.liukc.trainingsystem.dao.EmployeeDao;
import com.dgut.liukc.trainingsystem.javaBean.Article;
import com.dgut.liukc.trainingsystem.javaBean.Comment;
import com.dgut.liukc.trainingsystem.javaBean.Employee;
import com.dgut.liukc.trainingsystem.javaBean.Message;
import com.dgut.liukc.trainingsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    private Comment comment;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Comment insertComment(Map map) {
        // 1. 保存评论
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        comment = new Comment();
        entrySet.forEach(entry -> {
            switch (entry.getKey()) {
                case "articleId":
                    Article article = new Article();
                    article.setId((Integer) entry.getValue());
                    comment.setArticle(article);
                    break;
                case "content":
                    comment.setContent((String) entry.getValue());
                    break;
                case "empId":
                    Employee employee = employeeDao.searchEmployeeById((Integer) entry.getValue());
                    comment.setEmployee(employee);
                    break;
                case "receiver":
                    if (entry.getValue() != null) {
                        Employee receiver = employeeDao.searchEmployeeById((Integer) entry.getValue());
                        comment.setReceiver(receiver);
                    }
                    break;
            }
        });
        comment.setCreateTime(new Date());
        comment.setLike(0);
        commentDao.insertComment(comment);
        // 2. 发送消息
        Message message = new Message();
        message.setComment(comment);
        if (comment.getContent().length() > 15) {
            message.setContent(comment.getContent().substring(0, 15) + "...");
        } else {
            message.setContent(comment.getContent());
        }
        message.setCreateTime(new Date());
        message.setIsRead(0);
        Employee receiver = new Employee();
        if (comment.getReceiver() == null){
            receiver.setId(articleDao.searchEmpIdById(comment.getArticle().getId()));
        }else {
            receiver.setId(comment.getReceiver().getId());
        }
        message.setReceiver(receiver);
        Employee sender = new Employee();
        sender.setId(comment.getEmployee().getId());
        message.setSender(sender);
        commentDao.insertMessage(message);
        return comment;
    }

    @Override
    public List<Comment> searchCommentsByArticleId(int articleId) {
        return commentDao.searchCommentsByArticleId(articleId);
    }

    @Override
    public List<Message> searchMessagesByEmpId(int receiverId) {
        return commentDao.searchMessagesByEmpId(receiverId);
    }

    @Override
    public Article readMessage(Map map) {
        commentDao.readMessage((Integer) map.get("messageId"));
        return articleDao.searchArticleByCommentId((Integer) map.get("commentId"));
    }
}
