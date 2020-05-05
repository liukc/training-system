package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Option {
    private Integer id;
    private char order; // a, b, c, d
    private String description;
    private int isTrue;
}
