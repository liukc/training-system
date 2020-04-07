package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.TrainingSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void testChangeTrainingPeriod() {
        articleService.changeTrainingPeriod(7, "2020-04-03", "2020-04-05");
    }

    @Test
    public void testInitEmpLogs() {
        Calendar calendar = new GregorianCalendar(2019, 12, 27);
        Date date = calendar.getTime();
        calendar.add(Calendar.DATE, 14);
        Date date1 = calendar.getTime();
    }
}
