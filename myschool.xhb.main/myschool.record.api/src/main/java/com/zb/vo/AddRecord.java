package com.zb.vo;

import com.zb.pojo.Lable;

import java.util.List;

public class AddRecord {

    private Integer recordId;
    private String classId;
    private String userId;
    private String recordContent;
    private Integer visible;
    private List<Lable> lables;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
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

    public List<Lable> getLables() {
        return lables;
    }

    public void setLables(List<Lable> lables) {
        this.lables = lables;
    }
}
