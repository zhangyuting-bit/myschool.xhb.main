package com.zb.entity;

import java.io.Serializable;
import java.util.List;

//学员学号表
public class StuNumber implements Serializable {
    //学号编号
    private String numberId;
    //班级编号
    private String gradeId;
    //学生编号
    private String stuId;
    //学号
    private String stuNo;
    //学生姓名
    private String stuName;

    List<StuSubject>stuSubjects;

    public List<StuSubject> getStuSubjects() {
        return stuSubjects;
    }

    public void setStuSubjects(List<StuSubject> stuSubjects) {
        this.stuSubjects = stuSubjects;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }
}
