package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Exam {
    private Integer id;
    private String name;
    private double score;
    private int questionNumber;
    private List<Question> questions;

    public Exam() {
        List<Question> questions = new ArrayList<>();
        this.questions = questions;
    }
}
