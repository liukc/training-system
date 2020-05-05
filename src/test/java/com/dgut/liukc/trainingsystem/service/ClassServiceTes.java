package com.dgut.liukc.trainingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassServiceTes {
    @Autowired
    private ClassService classService;

    @Test
    public void testSearchClassAndEmpByTeacherId(){
        System.out.println(classService.searchClassAndEmpByTeacherId(11));
    }
}
