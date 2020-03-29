package com.zb.entity;

import java.io.Serializable;
//科目类型表
public class SubjectType implements Serializable {
    //科目类型编号
    private String id;
    //科目名称
    private String subjectName;

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
}
