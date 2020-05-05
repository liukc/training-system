package com.dgut.liukc.trainingsystem.service.impl;

import com.dgut.liukc.trainingsystem.dao.SourceDao;
import com.dgut.liukc.trainingsystem.javaBean.Source;
import com.dgut.liukc.trainingsystem.service.SourceService;
import com.dgut.liukc.trainingsystem.utils.MD5Encryption;
import com.dgut.liukc.trainingsystem.utils.PropertiesOP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SourceServiceImpl implements SourceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SourceDao sourceDao;

    @Override
    public Source sourceUpload(MultipartFile file, String desc, Integer empId, int isPrivate) {
        Source source = null;
        String md5;
        String path;
        try {
            md5 = MD5Encryption.getFileMd5(file);
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5 算法异常", e);
            return null;
        }
        Integer res = sourceDao.getSourceIdByMD5(md5);
        if (res == null){ // 文件不存在
            path = MD5Encryption.saveFile(file);
            if (!StringUtils.isEmpty(path)){
                path = path.replace("\\", "/");
                source = new Source();
                source.setName(file.getOriginalFilename());
                source.setDescription(desc);
                source.setRealPath(path);
                source.setHot(0);
                source.setMd5(md5);
                source.setSize(file.getSize());
                source.setType(file.getContentType());
                source.setIsPrivate(isPrivate);
                source.setEmpId(empId);
                String accessPath = PropertiesOP.getConfigValueByKey("access.path");
                String[] paths = path.split("/");
                accessPath = accessPath + "/" + paths[paths.length - 1];
                source.setAccessPath(accessPath);
                sourceDao.insertSource(source);
            }
        }else {
            source = sourceDao.searchSourceById(res);
            source.setIsPrivate(isPrivate);
        }
        return source;
    }

    @Override
    public List<Source> selectSources(String type) {
        if ("all".equals(type)){
            type="";
        }

        return sourceDao.selectSourcesByType(type);
    }

    @Override
    public List<Source> sourcesUpload(MultipartFile[] files, Integer empId, int isPrivate) {
        List<Source> sources = new ArrayList<>();
        for (MultipartFile file: files){
            sources.add(sourceUpload(file,null,empId,1));
        }
        return sources;
    }

    @Override
    public Source searchSourceById(int id) {
        return sourceDao.searchSourceById(id);
    }
}
