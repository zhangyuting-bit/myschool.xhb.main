package com.zb.vo;

import com.zb.pojo.Lable;

import java.util.List;

public class AddRecord {

    private String recordId;
    private String classId;
    private String userId;
    private String recordContent;
    private Integer visible;
    private String lables;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getLables() {
        return lables;
    }

    public void setLables(String lables) {
        this.lables = lables;
    }
}

