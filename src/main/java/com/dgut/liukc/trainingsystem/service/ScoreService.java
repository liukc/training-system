package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Score;

import java.util.List;

public interface ScoreService {

    List<Score> searchScoresByEmpId(int empId);

    String analyzeScores(List scores);
}
