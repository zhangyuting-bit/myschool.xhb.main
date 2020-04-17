package com.zb.entity;

import com.zb.pojo.Class_add;
import com.zb.pojo.TeacherInfo;

import java.io.Serializable;
import java.util.List;

//通知,习惯,讨论,作业,活动类
public class Notification implements Serializable {
    //通知编号
    private String notificationId;
    //类型编号
    private Integer typeId;
    //教师编号
    private String teacherId;
    //班级编号
    private String gradeId;
    //发布内容
    private String notifyMessage;
    //发布时间
    private String notifyTime;
    //发布标题
    private String title;
    //结束时间
    private String endTime;
    //音频路径
    private String audioSrc;
    //视频路径
    private String videoSrc;
    //状态为零的图片
    private NotPic notPic;
    //图片集合
    private List<NotPic>notPics;
    //附件集合
    private List<NotDocument>documents;
    //是否需要上传作业
    private Integer statu;
    //成果是否相互可见
    private Integer status;
    //教师信息
    private Class_add class_add;

    public Class_add getClass_add() {
        return class_add;
    }

    public void setClass_add(Class_add class_add) {
        this.class_add = class_add;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<NotDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<NotDocument> documents) {
        this.documents = documents;
    }

    public List<NotPic> getNotPics() {
        return notPics;
    }

    public void setNotPics(List<NotPic> notPics) {
        this.notPics = notPics;
    }

    public NotPic getNotPic() {
        return notPic;
    }

    public void setNotPic(NotPic notPic) {
        this.notPic = notPic;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getAudioSrc() {
        return audioSrc;
    }

    public void setAudioSrc(String audioSrc) {
        this.audioSrc = audioSrc;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
    }

    private String picSrc;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getNotifyMessage() {
        return notifyMessage;
    }

    public void setNotifyMessage(String notifyMessage) {
        this.notifyMessage = notifyMessage;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }


}
