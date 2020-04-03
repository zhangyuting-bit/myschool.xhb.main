package com.zb.entity;

import java.io.Serializable;
//科目成绩表
public class Subject implements Serializable {
    //科目成绩编号
    private String subjectId;
    //发布成绩编号
    private String scoreId;
    //科目类型编号
    private String type;
    //满分
    private String full;
    //最高分
    private String high;
    //平均分
    private double avg;
    //最低分
    private String low;
    //科目成绩
    private StuSubject stuSubject;

    public StuSubject getStuSubject() {
        return stuSubject;
    }

    public void setStuSubject(StuSubject stuSubject) {
        this.stuSubject = stuSubject;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

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
