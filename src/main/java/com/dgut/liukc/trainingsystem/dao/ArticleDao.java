package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Article;
import com.dgut.liukc.trainingsystem.javaBean.ArticleType;
import com.dgut.liukc.trainingsystem.javaBean.EmpJournal;
import com.dgut.liukc.trainingsystem.javaBean.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleDao {

    Integer isLogExist(@Param("id")int id);

    int insertLog(@Param("log")EmpJournal log);

    int updateLog(@Param("log")EmpJournal log);

    int insertLogList(@Param("logs")List logs);

    List selectLogsByEmpId(@Param("empId") int empId);

    List searchEditedLogsByEmpId(@Param("empId") int empId);

    EmpJournal selectLogById(@Param("id") int id);

    int deleteLogsByCreateTime(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("empId") int empId);

    int insertLogComment(@Param("log")EmpJournal empJournal);
   // article
    void insertArticle(@Param("article") Article article);

    Label searchLabelIsExistByName(@Param("name")String name);

    void insertLabel(@Param("label")Label label);

    ArticleType searchArticleTypeIsExistByName(@Param("name")String name);

    void insertArticleType(@Param("articleType")ArticleType articleType);

    void insertLabelArticle(@Param("article") Article article);

    Integer searchEmpIdById(@Param("id")int id);

    List<Article> searchArticlesByTypeId(@Param("typeId")int typeId);

    Article searchArticleById(@Param("id") int id);

    void updateArticle(@Param("article") Article article);

    Article searchArticleByCommentId(@Param("commentId")int commentId);

    List<Article> searchArticlesByTitle(@Param("word") String word);

    List<Article> searchArticlesByHot();

    List<Article> searchArticle();

    void deleteArticleById(@Param("id") Integer id);
}
