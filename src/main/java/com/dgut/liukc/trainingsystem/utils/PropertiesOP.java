package com.dgut.liukc.trainingsystem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author liukc
 * @description 操作 properties 文件
 */
public final class PropertiesOP {
    private static Logger logger = LoggerFactory.getLogger(PropertiesOP.class);

    private static final String STATUS_MESSAGE = "/config/status-message.properties";

    private static final String CONFIG = "/config/config.properties";

    private PropertiesOP() {
    }

    public static String getMessageByStatus(int status) {
        return readProperties(String.valueOf(status), STATUS_MESSAGE);
    }

    public static String getConfigValueByKey(String key){
        return readProperties(key, CONFIG);
    }

    /**
     * 读取配置文件
     *
     * @param key 配置文件中的 key
     * @return 返回对应的 value；null：读取失败
     */
    public static String readProperties(String key, String path) {
        Properties properties = new Properties();
        try (InputStreamReader inputStreamReader = new InputStreamReader(PropertiesOP.class.getResourceAsStream(path))) {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            logger.error("配置文件读取异常...", e);
            return null;
        }
        return properties.getProperty(key);
    }
}
