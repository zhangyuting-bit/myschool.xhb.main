package com.zb.entity;

import java.io.Serializable;
//科目成绩表
public class Subject implements Serializable {
    ///科目成绩编号
    private String subjectId;
    //发布成绩编号
    private String scoreId;
    //科目类型编号
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }
}
