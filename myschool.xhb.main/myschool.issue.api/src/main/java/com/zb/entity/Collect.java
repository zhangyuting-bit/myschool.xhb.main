package com.zb.entity;

import java.io.Serializable;

//消息收藏类
public class Collect implements Serializable {
        //收藏编号
    private String collectId;
    //类型编号
    private String typeId;
    //消息编号
    private String id;
    //用户编号
    private String userId;

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

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
