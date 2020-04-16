package com.zb.pojo;

import java.io.Serializable;

/**
 * 课程表
 */
public class Class_Timetable implements Serializable {
    private String id;
    private Integer class_number;
    private String teacherName;
    private String class_name;
    private String class_emblem;
    private String timetableimage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getClass_number() {
        return class_number;
    }

    public void setClass_number(Integer class_number) {
        this.class_number = class_number;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTimetableimage() {
        return timetableimage;
    }

    public void setTimetableimage(String timetableimage) {
        this.timetableimage = timetableimage;
    }

    public String getClass_emblem() {
        return class_emblem;
    }

    public void setClass_emblem(String class_emblem) {
        this.class_emblem = class_emblem;
    }
}
