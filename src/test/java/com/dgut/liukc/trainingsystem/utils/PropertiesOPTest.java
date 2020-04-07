package com.dgut.liukc.trainingsystem.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesOPTest {

    @Test
    public void testGetMessageByStatus(){
        System.out.println(PropertiesOP.getMessageByStatus(4001));
    }
}
