package com.zb.entity;

import java.io.Serializable;

//题目选项类
public class Answer implements Serializable {
    //选项编号
    private String answerId;
    //题目编号
    private String selectId;
    //题目选项文字
    private String answer;
    //题目图片
    private String picSrc;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }
}
