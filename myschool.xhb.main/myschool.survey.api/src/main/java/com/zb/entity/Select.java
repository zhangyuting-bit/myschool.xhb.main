package com.zb.entity;

import java.io.Serializable;
import java.util.List;

//题目类
public class Select implements Serializable {
    //题目编号
    private String selectId;
    //题目类型
    private String type;
    //调查编号
    private String surveyId;
    //问题
    private String question;
    //音频路径
    private String audioSrc;
    //0代表必答,1代表不是必答
    private Integer status;
    //题目图片集合
    private List<SelectPic>selectPics;
    //题目答案集合
    private List<Answer>answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<SelectPic> getSelectPics() {
        return selectPics;
    }

    public void setSelectPics(List<SelectPic> selectPics) {
        this.selectPics = selectPics;
    }

    public String getAudioSrc() {
        return audioSrc;
    }

    public void setAudioSrc(String audioSrc) {
        this.audioSrc = audioSrc;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
