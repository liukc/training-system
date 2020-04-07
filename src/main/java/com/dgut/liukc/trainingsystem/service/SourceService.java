package com.dgut.liukc.trainingsystem.service;

import com.dgut.liukc.trainingsystem.javaBean.Source;
import org.springframework.web.multipart.MultipartFile;

public interface SourceService {

    Source sourceUpload(MultipartFile file, String desc, Integer empId);
}
