package com.zb.entity;

import java.io.Serializable;
//定时任务表
public class JobTask implements Serializable {
    //定时编号
    private String id;
    //通知编号
    private String notificationId;
    //班级编号
    private String gradeId;
    //提醒时间
    private String taskTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
