package com.zb.pojo;

import java.io.Serializable;

public class Lable implements Serializable {

    private Integer lableId;
    private String lableContent;
    private Integer lableType;
    private Integer userId;

    public Integer getLableId() {
        return lableId;
    }

    public void setLableId(Integer lableId) {
        this.lableId = lableId;
    }

    public String getLableContent() {
        return lableContent;
    }

    public void setLableContent(String lableContent) {
        this.lableContent = lableContent;
    }

    public Integer getLableType() {
        return lableType;
    }

    public void setLableType(Integer lableType) {
        this.lableType = lableType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
