package com.zb.vo;

import java.io.Serializable;
//班级工具类
public class ClassVo implements Serializable {
    //班级编号
    private String gradeId;
    //用户编号
    private String userId;
    //用户姓名
    private String userName;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
