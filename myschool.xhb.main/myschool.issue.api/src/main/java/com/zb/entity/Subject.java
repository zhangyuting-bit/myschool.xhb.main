package com.zb.entity;

import java.io.Serializable;
//科目成绩表
public class Subject implements Serializable {
    //科目成绩编号
    private String subjectId;
    //发布成绩编号
    private String scoreId;
    //学员学号编号
    private String numberId;
    //科目类型编号
    private String typeId;
    //新增科目类型编号
    private String addId;
    //科目分数
    private String score;

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

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
