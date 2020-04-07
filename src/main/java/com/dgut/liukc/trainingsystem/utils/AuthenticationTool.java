package com.dgut.liukc.trainingsystem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public final class AuthenticationTool {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationTool.class);

    private AuthenticationTool() {
    }

    @Autowired
    private static RedisTemplate redisTemplate;

    public static Integer checkToken(String token) {
        Object id = redisTemplate.opsForValue().get(token);
        return id == null ? null : (Integer) id;
    }
}
