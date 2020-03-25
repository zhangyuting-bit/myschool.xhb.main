package com.zb.entity;

import java.io.Serializable;
//模板类
public class Mudel implements Serializable {
    //模板编号
    private Integer mudelId;
    //通知类型
    private Integer typeId;
    //模板标题
    private String mudelTitle;
    //模板内容
    private String mudelMessage;
    //模板图片
    private String mudelPic;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getMudelId() {
        return mudelId;
    }

    public void setMudelId(Integer mudelId) {
        this.mudelId = mudelId;
    }

    public String getMudelTitle() {
        return mudelTitle;
    }

    public void setMudelTitle(String mudelTitle) {
        this.mudelTitle = mudelTitle;
    }

    public String getMudelMessage() {
        return mudelMessage;
    }

    public void setMudelMessage(String mudelMessage) {
        this.mudelMessage = mudelMessage;
    }

    public String getMudelPic() {
        return mudelPic;
    }

    public void setMudelPic(String mudelPic) {
        this.mudelPic = mudelPic;
    }
}
