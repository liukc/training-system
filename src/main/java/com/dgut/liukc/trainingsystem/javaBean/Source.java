package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Source {
    private Integer id;
    private String name;
    private String description;
    private String coverImage;
    private String type;
    private double size;
    private String realPath;
    private String accessPath;
    private String md5;
    private int hot;
    private int isPrivate;
    private Integer empId;
    private String empName;
}
