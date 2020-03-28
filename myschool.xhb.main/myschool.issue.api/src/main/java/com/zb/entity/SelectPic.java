package com.zb.entity;

import java.io.Serializable;
//题目图片类
public class SelectPic implements Serializable {
    //图片编号
    private String picId;
    //题目编号
    private String selectId;
    //图片路径
    private String picSrc;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }
}
