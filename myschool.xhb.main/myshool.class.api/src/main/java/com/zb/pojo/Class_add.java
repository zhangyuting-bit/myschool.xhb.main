package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
*   pojo of class_add
*/
public class Class_add implements Serializable {
    //
    private String id;
    //班级编号
    private Integer class_number;
    //班级名称
    private String class_Name;
    //班级的班徽图片
    private String class_emblem;
    //学科：任教老师的学科
    private Integer class_subject;
    private String subjectName;
    //所属学校
    private String school;
    //学历
    private Integer real_id;
    //年纪
    private Integer age_id;
    //几班
    private String real_class;
    //老师的id
    private Integer teacher_id;
    private String teacherName;
    //总人数
    public Integer class_count;
    private  String createdTime;
    private String updatedTime;
    //班级学生编号集合
    private List<String>userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

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
    public void setClass_Name (String  class_Name){
        this.class_Name=class_Name;
    }
    public  String getClass_Name(){
        return this.class_Name;
    }
    public void setClass_emblem (String  class_emblem){
        this.class_emblem=class_emblem;
    }
    public  String getClass_emblem(){
        return this.class_emblem;
    }
    public void setClass_subject (Integer  class_subject){
        this.class_subject=class_subject;
    }
    public  Integer getClass_subject(){
        return this.class_subject;
    }
    public void setSchool (String  school){
        this.school=school;
    }
    public  String getSchool(){
        return this.school;
    }
    public void setReal_id (Integer  real_id){
        this.real_id=real_id;
    }
    public  Integer getReal_id(){
        return this.real_id;
    }
    public void setAge_id (Integer  age_id){
        this.age_id=age_id;
    }
    public  Integer getAge_id(){
        return this.age_id;
    }
    public void setReal_class (String  real_class){
        this.real_class=real_class;
    }
    public  String getReal_class(){
        return this.real_class;
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

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getClass_count() {
        return class_count;
    }

    public void setClass_count(Integer class_count) {
        this.class_count = class_count;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
