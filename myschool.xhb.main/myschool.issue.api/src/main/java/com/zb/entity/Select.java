package com.zb.entity;

import java.io.Serializable;
//题目类
public class Select implements Serializable {
    //题目编号
    private String selectId;
    //题目类型
    private String type;
    //调查编号
    private String surveyId;
    //问题
    private String question;
    //0代表必答,1代表不是必答
    private Integer status;

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
