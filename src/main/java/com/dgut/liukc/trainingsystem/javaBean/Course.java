package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Course {
    private Integer id;
    private String name;
    private Exam exam;
    private List<Class> classes;
    private List<Source> sources;

    public Course(){
        this.classes = new ArrayList<>();
        this.sources = new ArrayList<>();
    }
}
