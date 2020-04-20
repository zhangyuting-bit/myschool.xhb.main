package com.zb.entity;

import java.io.Serializable;
import java.util.List;

//用户类
public class User implements Serializable {
    private String userId;
    private String gradeId;
    private String userName;
    private List<Grade>grades;

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
