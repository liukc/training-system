package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@Scope("prototype")
public class Detail {

    private int status;
    private String message;
    private Map map;

    Detail(){
        status = 2000;
        message = "成功";
        map = new HashMap<Object, Object>();
    }

    public void clear(){
        status = 2000;
        message = "成功";
        map.clear();
    }
}
