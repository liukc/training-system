package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Question {
    private Integer id;
    private String type;
    private String description;
    private double score;
    private int order;
    private List<Option> options;

    public Question() {
        List<Option> options = new ArrayList<>();
        this.options = options;
    }
}
