package com.dgut.liukc.trainingsystem.javaBean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class EmpJournal {

    private Integer id;
    private Date createTime;
    private String week;
    private String content;
    private String feedback;
    private String comment;
    private Integer isEdit;
    private Integer empId;

    public EmpJournal() {
    }

    public EmpJournal(EmpJournal empJournal) {
        this.id = empJournal.getId();
        this.createTime = empJournal.getCreateTime();
        this.content = empJournal.getContent();
        this.feedback = empJournal.getFeedback();
        this.comment = empJournal.getComment();
        this.empId = empJournal.getEmpId();
    }
}
