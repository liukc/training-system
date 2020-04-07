package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.ArticleDao;
import com.dgut.liukc.trainingsystem.dao.EmployeeDao;
import com.dgut.liukc.trainingsystem.javaBean.EmpJournal;
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

    @Override
    public EmpJournal editPersonalLog(EmpJournal empJournal) {
        empJournal.setIsEdit(1);
        int result = articleDao.updateLog(empJournal);
        if (result == 1){
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
}
