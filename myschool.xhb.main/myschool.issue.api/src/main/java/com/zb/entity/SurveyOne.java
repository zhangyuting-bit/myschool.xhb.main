package com.zb.entity;

import java.io.Serializable;
////调查用户类
public class SurveyOne implements Serializable {
    //
    private String oneId;
    //调查编号
    private String surveyId;
    //用户编号
    private String userId;
    //增加时间
    private String createTime;

    public String getOneId() {
        return oneId;
    }

    public void setOneId(String oneId) {
        this.oneId = oneId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
