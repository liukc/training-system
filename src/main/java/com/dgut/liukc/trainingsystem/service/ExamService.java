package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Exam;
import com.dgut.liukc.trainingsystem.javaBean.Score;

import java.util.Map;

public interface ExamService {
    public Exam insertExam(Map map);

    Exam searchExamById(int id);

    Score doExam(Exam exam, int empId);
}
