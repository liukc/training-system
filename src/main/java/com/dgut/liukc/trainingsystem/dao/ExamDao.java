package com.dgut.liukc.trainingsystem.dao;

import com.dgut.liukc.trainingsystem.javaBean.Exam;
import com.dgut.liukc.trainingsystem.javaBean.Option;
import com.dgut.liukc.trainingsystem.javaBean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamDao {

    void insertQuestions(@Param("questions") List<Question> questions, @Param("examId")Integer examId);

    void insertOptions(@Param("options") List<Option> options, @Param("questionId")Integer questionId);

    void insertExam(@Param("exam") Exam exam);

    Exam searchExamById(@Param("id") int id);

    List<Question> searchQuestionsByExamId(@Param("examId")int examId);

    List<Option> searchOptionsByQuestionId( @Param("questionId")Integer questionId);

    Option searchOptionById(@Param("id") int i);
}
