package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Article;
import com.dgut.liukc.trainingsystem.javaBean.EmpJournal;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    EmpJournal editPersonalLog(EmpJournal empJournal);

    List changeTrainingPeriod(int empId, String beginDate, String endDate);

    List initEmpLogs(int empId, Date beginDate, Date endDate);

    List getLogs(int empId);

    List getEditedLogs(int empId);

    EmpJournal selectLogById(int id);

    default int isLegalBeginDate(String beginDate) {
        return 0;
    }

    int insertLogComment(EmpJournal empJournal);

    Article insertArticle(Map map);

    List<Article> searchArticlesByTypeId(int typeId);

    Article searchArticleById(int articleId);

    List<Article> searchArticlesByTitle(String word);

    List<Article> searchArticlesByHot();

    List<Article> searchArticles();

    void deleteArticleById(Integer id);
}
