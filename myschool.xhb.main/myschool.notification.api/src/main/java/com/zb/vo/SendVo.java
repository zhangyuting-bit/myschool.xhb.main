package com.zb.vo;

import java.io.Serializable;

public class SendVo implements Serializable {
    //老师姓名
    private String teacherName;
    //班级名称
    private String className;
    //学生编号
    private String userId;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
