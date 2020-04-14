package com.zb.pojo;

import java.io.Serializable;

public class TeacherInfo implements Serializable {
    private String id;
    private String teacherName;
    private String userId;
    private String phone;
    private String schoolname;
    private String inviteCode;
    private String status;
    private String createTime;
    private String updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public TeacherInfo() {
    }

    public TeacherInfo(String id, String teacherName, String phone, String schoolname, String inviteCode, String status, String createTime, String updateTime) {
        this.id = id;
        this.teacherName = teacherName;
        this.phone = phone;
        this.schoolname = schoolname;
        this.inviteCode = inviteCode;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
