package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.ArticleDao;
import com.dgut.liukc.trainingsystem.dao.EmployeeDao;
import com.dgut.liukc.trainingsystem.javaBean.*;
import com.dgut.liukc.trainingsystem.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("LogServiceImpl")
public class LogServiceImpl implements ArticleService {

    private String DATE_FORMAT = "yyyy-MM-dd";
    private String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private EmployeeDao employeeDao;

    private Article article;

    @Override
    public EmpJournal editPersonalLog(EmpJournal empJournal) {
        empJournal.setIsEdit(1);
        int result = articleDao.updateLog(empJournal);
        if (result == 1) {
            return articleDao.selectLogById(empJournal.getId());
        }
        return null;
    }

    @Override
    public List<EmpJournal> changeTrainingPeriod(int empId, String beginDate, String endDate) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        List<EmpJournal> logs = null;
        Date bDate;
        Date eDate;
        try {
            bDate = dateFormat.parse(beginDate);
            eDate = dateFormat.parse(endDate);
        } catch (ParseException e) {
            logger.error("日期格式转换失败...", e);
            return logs;
        }

        if (employeeDao.updateTrainingPeriod(empId, bDate, eDate) == 1) {
            logs = articleDao.selectLogsByEmpId(empId);
            // 修改的情况
            if (logs.size() != 0) {
                // 包含两种情况，一种是结束日期，一种是开始日期
                changeEmpLogsBeginPeriod(empId, logs, bDate);
                changeEmpLogsEndPeriod(empId, logs, eDate);

            } else {
                logs.addAll(initEmpLogs(empId, bDate, eDate));
            }
            logs.sort(Comparator.comparing(EmpJournal::getCreateTime));
        }
        return logs;
    }

    public List<EmpJournal> changeEmpLogsBeginPeriod(int empId, List<EmpJournal> logs, Date beginDate) {
        if (logs.get(logs.size() - 1).getCreateTime().compareTo(beginDate) > 0) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(logs.get(logs.size() - 1).getCreateTime());
            calendar.add(Calendar.DATE, -1);
            logs.addAll(initEmpLogs(empId, beginDate, calendar.getTime()));
        } else {
            articleDao.deleteLogsByCreateTime(beginDate, null, empId);
            for (int i = logs.size() - 1; i >= 0; i--) {
                if (logs.get(i).getCreateTime().compareTo(beginDate) < 0) {
                    logs.remove(i);
                }
            }
        }
        return logs;
    }

    public List<EmpJournal> changeEmpLogsEndPeriod(int empId, List<EmpJournal> logs, Date endDate) {
        if (logs.get(0).getCreateTime().compareTo(endDate) < 0) { // 添加的情况
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(logs.get(0).getCreateTime());
            calendar.add(Calendar.DATE, 1);
            logs.addAll(initEmpLogs(empId, calendar.getTime(), endDate));
        } else {
            articleDao.deleteLogsByCreateTime(null, endDate, empId);
            for (int i = logs.size() - 1; i >= 0; i--) {
                if (logs.get(i).getCreateTime().compareTo(endDate) > 0) {
                    logs.remove(i);
                }
            }
        }
        return logs;
    }

    @Override
    public List initEmpLogs(int empId, Date beginDate, Date endDate) {
        Calendar calendar = new GregorianCalendar();
        EmpJournal empJournal = new EmpJournal();
        empJournal.setEmpId(empId);
        List<EmpJournal> empJournals = new LinkedList<>();
        calendar.setTime(beginDate);
        Date midDate = calendar.getTime();
        while (midDate.compareTo(endDate) <= 0) {
            EmpJournal empJournalClone = new EmpJournal(empJournal);
            empJournalClone.setCreateTime(midDate);
            empJournalClone.setWeek(weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            empJournals.add(empJournalClone);
            calendar.add(Calendar.DATE, 1);
            midDate = calendar.getTime();
        }
        int number = articleDao.insertLogList(empJournals);
        if (number < empJournals.size()) {
            logger.error("日志插入失败条数{}", empJournals.size() - number);
        }
        return empJournals;
    }

    @Override
    public List getLogs(int empId) {
        return articleDao.selectLogsByEmpId(empId);
    }

    @Override
    public List getEditedLogs(int empId) {
        return articleDao.searchEditedLogsByEmpId(empId);
    }

    @Override
    public EmpJournal selectLogById(int id) {
        return articleDao.selectLogById(id);
    }

    @Override
    public int isLegalBeginDate(String beginDate) {
        Date bDate;
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            bDate = dateFormat.parse(beginDate);
        } catch (ParseException e) {
            logger.error("日期格式转换失败...", e);
            return -1;
        }
        if (bDate.compareTo(new Date()) >= 0) {
            return -1;
        }
        return 1;
    }

    @Override
    public int insertLogComment(EmpJournal empJournal) {
        return articleDao.insertLogComment(empJournal);
    }

    @Override
    public Article insertArticle(Map map) {
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        article = new Article();
        entrySet.forEach(entry -> {
            switch (entry.getKey()) {
                case "title":
                    article.setTitle((String) entry.getValue());
                    break;
                case "label":
                    if (entry.getValue() instanceof List) {
                        ((List) entry.getValue()).forEach(label -> {
                            Label newLabel;
                            String labelName = (String) label;
                            newLabel = articleDao.searchLabelIsExistByName(labelName);
                            if (newLabel == null) {
                                newLabel = new Label();
                                newLabel.setName(labelName);
                                articleDao.insertLabel(newLabel);
                            }
                            article.getLabels().add(newLabel);
                        });
                    }
                    break;
                case "type":
                    ArticleType articleType;
                    String articleTypeName = (String) entry.getValue();
                    articleType = articleDao.searchArticleTypeIsExistByName(articleTypeName);
                    if (articleType == null) {
                        articleType = new ArticleType();
                        articleType.setName(articleTypeName);
                        articleDao.insertArticleType(articleType);
                    }
                    article.setArticleType(articleType);
                    break;
                case "content":
                    article.setContent((String) entry.getValue());
                    break;
                case "coverImg":
                    article.setCoverImg((String) entry.getValue());
                    break;
                case "empId":
                    Employee employee = new Employee();
                    employee.setId((Integer) entry.getValue());
                    article.setEmployee(employee);
                    break;
                default:
                    break;
            }
        });
        article.setCreateTime(new Date());
        if (article.getContent().length() > 100) {
            article.setDescription(article.getContent().substring(0, 100).replaceAll("\\<.*?>", ""));
        } else {
            article.setDescription(article.getContent().replaceAll("\\<.*?>", ""));
        }
        article.setHot(0);
        articleDao.insertArticle(article);
        articleDao.insertLabelArticle(article);
        article.setEmployee(employeeDao.searchEmployeeById(article.getEmployee().getId()));
        return article;
    }

    @Override
    public List<Article> searchArticlesByTypeId(int typeId) {
        return articleDao.searchArticlesByTypeId(typeId);
    }

    @Override
    public Article searchArticleById(int articleId) {
        Article article = articleDao.searchArticleById(articleId);
        article.setHot(article.getHot()+1);
        articleDao.updateArticle(article);
        return article;
    }

    @Override
    public List<Article> searchArticlesByTitle(String word) {
        return articleDao.searchArticlesByTitle(word);
    }

    @Override
    public List<Article> searchArticlesByHot() {
        return articleDao.searchArticlesByHot();
    }

    @Override
    public List<Article> searchArticles() {
        return articleDao.searchArticle();
    }

    @Override
    public void deleteArticleById(Integer id) {
        articleDao.deleteArticleById(id);
    }
}
