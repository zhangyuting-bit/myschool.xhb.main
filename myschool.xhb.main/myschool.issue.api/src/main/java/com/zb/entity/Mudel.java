package com.zb.entity;

import java.io.Serializable;

public class Mudel implements Serializable {
    private Integer mudelId;
    private Integer typeId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    private String mudelTitle;
    private String mudelMessage;
    private String mudelPic;

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
