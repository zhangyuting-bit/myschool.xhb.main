package com.zb.entity;

import java.io.Serializable;
//通知用户类
public class NotOne implements Serializable {
    //
    private String oneId;
    //通知编号
    private String functionId;
    //用户编号
    private String userId;
    //通知类型
    private Integer typeId;
    //增加时间
    private String createTime;
    //通知消息
    private Notification notification;
    //调查信息
    private Survey survey;
    //成绩信息
    private Score score;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getOneId() {
        return oneId;
    }

    public void setOneId(String oneId) {
        this.oneId = oneId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
