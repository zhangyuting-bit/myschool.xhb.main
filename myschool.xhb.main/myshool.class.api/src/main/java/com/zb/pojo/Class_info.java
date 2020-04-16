package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;

/**
*   pojo of class_info
*/
public class Class_info implements Serializable {
    //
    private String id;
    //班级编号
    private Integer class_number;
    //登录者的权限id
    private Integer jurisdiction_id;
    //登录者的编号
    private Integer user_id;
    //班级内的称呼
    private String call;
    //家访的备注
    private String remarks;
    //班级内部的权限
    private String jurisdiction;
    //任课学科的id
    private Integer class_subject;
    //原因
    private String reason;
    //状态
    private Integer state;
    //人数
    private Integer number;
    //关系
    private String relationship;
    private  String createdTime;
    private String updatedTime;
    //getter setter
    public void setId (String  id){
        this.id=id;
    }
    public  String getId(){
        return this.id;
    }
    public void setClass_number (Integer  class_number){
        this.class_number=class_number;
    }
    public  Integer getClass_number(){
        return this.class_number;
    }
    public void setCall (String  call){
        this.call=call;
    }
    public  String getCall(){
        return this.call;
    }
    public void setRemarks (String  remarks){
        this.remarks=remarks;
    }
    public  String getRemarks(){
        return this.remarks;
    }
    public void setReason (String  reason){
        this.reason=reason;
    }
    public  String getReason(){
        return this.reason;
    }
    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getClass_subject() {
        return class_subject;
    }

    public void setClass_subject(Integer class_subject) {
        this.class_subject = class_subject;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Integer getJurisdiction_id() {
        return jurisdiction_id;
    }

    public void setJurisdiction_id(Integer jurisdiction_id) {
        this.jurisdiction_id = jurisdiction_id;
    }
}
