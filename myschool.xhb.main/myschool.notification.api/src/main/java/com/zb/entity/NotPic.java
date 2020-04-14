package com.zb.entity;

import java.io.Serializable;

//通知,习惯,讨论,作业,活动图片类
public class NotPic implements Serializable {
    //图片编号
    private String picId;
    //通知编号
    private String functionId;
    //图片路径
    private String picSrc;
    //是否为第一张图片
    private Integer statu;

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }
}
