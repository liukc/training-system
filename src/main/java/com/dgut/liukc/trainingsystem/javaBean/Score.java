package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Score {
    private Integer id;
    private int rank; // 课程观看完成率
    private double number;  // 分数
    private Course course;
    private Integer empId;
}
