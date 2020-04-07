package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.EmpJournal;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ArticleService {

    public EmpJournal editPersonalLog(EmpJournal empJournal);

    public List changeTrainingPeriod(int empId, String beginDate, String endDate);

    public List initEmpLogs(int empId, Date beginDate, Date endDate);

    public List getLogs(int empId);

    public EmpJournal selectLogById(int id);

    public default int isLegalBeginDate(String beginDate){
        return 0;
    }

}
