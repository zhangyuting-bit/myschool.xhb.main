package com.zb.entity;

import java.io.Serializable;
//学生科目成绩类
public class StuSubject implements Serializable {
    ///编号
    private String sjId;
    //科目编号
    private String subjectId;
    //学号编号
    private String numberId;
    //分数
    private String score;

    public String getSjId() {
        return sjId;
    }

    public void setSjId(String sjId) {
        this.sjId = sjId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
