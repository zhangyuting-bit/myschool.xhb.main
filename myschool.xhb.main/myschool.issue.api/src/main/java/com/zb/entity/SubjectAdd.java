package com.zb.entity;

import java.io.Serializable;
//新增科目表
public class SubjectAdd implements Serializable {
    //科目编号
    private String id;
    //科目名称
    private String subjectName;
    //班级编号
    private String gradeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
}
