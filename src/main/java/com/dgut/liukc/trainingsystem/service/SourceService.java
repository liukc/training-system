package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Source;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SourceService {

    Source sourceUpload(MultipartFile file, String desc, Integer empId, int isPrivate);

    List<Source> selectSources(String type);

    List<Source> sourcesUpload(MultipartFile[] files, Integer empId, int isPrivate);

    Source searchSourceById(int id);

    List<Source> searchSourcesByHot(String type);

    void deleteSourceById(Integer id);
}
