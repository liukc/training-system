package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.ScoreDao;
import com.dgut.liukc.trainingsystem.javaBean.Score;
import com.dgut.liukc.trainingsystem.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreDao scoreDao;


    @Override
    public List<Score> searchScoresByEmpId(int empId) {
        return scoreDao.searchScoresByEmpId(empId);
    }

    @Override
    public String analyzeScores(List scores) {
        return null;
    }
}
