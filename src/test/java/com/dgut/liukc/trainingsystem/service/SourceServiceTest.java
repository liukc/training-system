package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Source;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SourceServiceTest {

    @Autowired
    SourceService sourceService;

    @Test
    public void testSelectSourcesByType(){
        System.out.println(sourceService.selectSources("all"));
    }
}
