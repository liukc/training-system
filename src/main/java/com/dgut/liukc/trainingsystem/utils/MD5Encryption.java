package com.dgut.liukc.trainingsystem.utils;

import com.dgut.liukc.trainingsystem.javaBean.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * MD5 进行密码加密，密码校验及文件校验等
 * @author liukc
 */
public final class MD5Encryption {

    private static Logger logger = LoggerFactory.getLogger(MD5Encryption.class);

    private MD5Encryption(){}

    /**
     * 用于对密码进行MD5加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();

        return base64en.encode(messageDigest.digest(password.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 通过员工信息和盐生成token
     * @param employee 员工
     * @param salt 盐
     * @return 返回token
     * @throws NoSuchAlgorithmException 不存在该算法
     */
    public static String generateTokenByUserInfo(Employee employee, String salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String empAndSalt = employee.toString()+salt;
        return base64en.encode(messageDigest.digest(empAndSalt.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 验证密码
     * @param password 数据库的密码
     * @param inputtedPassword 输入的密码
     * @return 验证结果
     * @throws NoSuchAlgorithmException 不存在该算法
     */
    public static boolean checkPassword(String password, String inputtedPassword) throws NoSuchAlgorithmException {
        if (password == null || inputtedPassword == null) {
            return false;
        }
        return password.equals(encryptPassword(inputtedPassword));
    }

    /**
     * 获取文件MD5
     *
     * @return 文件的 MD5 编码; null: 文件转换流出现异常
     */
    public static String getFileMd5(MultipartFile fileItem) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = new byte[1024 * 1024 * 10];
        int length = -1;
        try (InputStream fileInputStream = fileItem.getInputStream()) {
            while ((length = fileInputStream.read(bytes, 0, 1024 * 1024)) != -1) {
                messageDigest.update(bytes, 0, length);
            }
        } catch (IOException e) {
            logger.error("文件转换流出现异常", e);
            return null;
        }
        byte[] md5Bytes = messageDigest.digest();
        BigInteger bigInteger = new BigInteger(1, md5Bytes);
        return bigInteger.toString();
    }

    public static String saveFile(MultipartFile fileItem) {
        String fileLocation = null;
        String path = PropertiesOP.getConfigValueByKey("file.upload.path");
        String f = UUID.randomUUID() + fileItem.getOriginalFilename().substring(fileItem.getOriginalFilename().lastIndexOf("."));
        try {
            fileItem.transferTo(new File(path, f));
        } catch (IOException e) {
            logger.error("文件写入异常...", e);
            return null;
        }
        fileLocation = path + "/" + f;
        return fileLocation;
    }

}
