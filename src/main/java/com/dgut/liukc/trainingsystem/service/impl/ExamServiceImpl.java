package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.CourseDao;
import com.dgut.liukc.trainingsystem.dao.ExamDao;
import com.dgut.liukc.trainingsystem.dao.ScoreDao;
import com.dgut.liukc.trainingsystem.javaBean.Exam;
import com.dgut.liukc.trainingsystem.javaBean.Option;
import com.dgut.liukc.trainingsystem.javaBean.Question;
import com.dgut.liukc.trainingsystem.javaBean.Score;
import com.dgut.liukc.trainingsystem.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private Exam exam;

    @Autowired
    private ExamDao examDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ScoreDao scoreDao;

    String isTrue;

    @Override
    public Exam insertExam(Map map) {
        Exam exam = new Exam();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        entrySet.forEach(entry -> {
            if ("name".equals(entry.getKey())) {
                exam.setName((String) entry.getValue());
            } else {
                List<Map> questions = (List<Map>) entry.getValue();
                questions.forEach(questionEntry -> {
                    Question question = new Question();
                    insertQuestion(question, questionEntry);
                    exam.getQuestions().add(question);
                    exam.setScore(exam.getScore() + question.getScore());
                });
            }
        });
        exam.setQuestionNumber(exam.getQuestions().size());
        examDao.insertExam(exam);
        examDao.insertQuestions(exam.getQuestions(), exam.getId());
        exam.getQuestions().forEach(question -> {
            examDao.insertOptions(question.getOptions(), question.getId());
        });
        return exam;
    }

    @Override
    public Exam searchExamById(int id) {
        Exam exam = examDao.searchExamById(id);
        exam.setQuestions(examDao.searchQuestionsByExamId(exam.getId()));
        exam.getQuestions().forEach(question -> {
            question.setOptions(examDao.searchOptionsByQuestionId(question.getId()));
        });
        return exam;
    }

    @Override
    public Score doExam(Exam exam, int empId) {
        Score score = new Score();
        score.setEmpId(empId);
        score.setRank(1);
        score.setCourse(courseDao.searchCourseByExamId(exam.getId()));
        double number = 0.0;
        for (Question question : exam.getQuestions()) {
            int count = 0; //记录错误答案数
            for (Option option : question.getOptions()) {
                Option optionDB = examDao.searchOptionById(option.getId());
                if (option.getIsTrue() != optionDB.getIsTrue()) {
                    count++;
                    continue;
                }
            }
            if (count == 0) {
                number = number + question.getScore();
            }
        }
        score.setNumber(number);
        scoreDao.insertScore(score);
        return score;
    }

    private void insertQuestion(Question question, Map questionMap) {
        Set<Map.Entry<String, Object>> questionEntry = ((Map) questionMap).entrySet();
        questionEntry.forEach(entry -> {
            switch (entry.getKey()) {
                case "description":
                    question.setDescription((String) entry.getValue());
                    break;
                case "type":
                    question.setType((String) entry.getValue());
                    break;
                case "score":
                    question.setScore(Double.valueOf((String) entry.getValue()));
                    break;
                case "order":
                    question.setOrder((Integer) entry.getValue());
                    break;
                case "answer":
                    if (entry.getValue() instanceof List) {
                        isTrue = entry.getValue().toString();
                    } else {
                        isTrue = (String) entry.getValue();
                    }
                    break;
                case "options":
                    List<Map> options = (List<Map>) entry.getValue();
                    options.forEach(optionEntry -> {
                        Option option = new Option();
                        insertOption(option, optionEntry);
                        question.getOptions().add(option);
                    });
            }
        });
    }

    private void insertOption(Option option, Map optionMap) {
        Set<Map.Entry<String, Object>> optionEntry = ((Map) optionMap).entrySet();
        optionEntry.forEach(entry -> {
            if ("order".equals(entry.getKey())) {
                option.setOrder(((String) entry.getValue()).charAt(0));
                option.setIsTrue(isTrue.contains((String) entry.getValue()) ? 1 : 0);
            } else if ("description".equals(entry.getKey())) {
                option.setDescription((String) entry.getValue());
            }
        });
    }
}
