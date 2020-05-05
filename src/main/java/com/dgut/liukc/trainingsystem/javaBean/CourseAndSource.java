package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CourseAndSource {
    private int id;
    private int order;
    private int courseId;
    private int sourceId;
}
