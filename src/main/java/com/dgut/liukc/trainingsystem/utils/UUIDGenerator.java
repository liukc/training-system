package com.dgut.liukc.trainingsystem.utils;

import java.util.UUID;

/**
 * 生成UUID
 * @author liukc
 */
public final class UUIDGenerator {
    private UUIDGenerator() {
    }

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}
