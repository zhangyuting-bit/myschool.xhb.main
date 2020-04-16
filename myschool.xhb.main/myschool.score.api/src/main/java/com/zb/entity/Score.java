package com.zb.entity;

import com.zb.pojo.Class_add;

import java.io.Serializable;
import java.util.List;

//发布成绩表
public class Score implements Serializable {
    //发布成绩编号
    private String scoreId;
    //通知类型
    private Integer typeId;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    //教师编号
    private String teacherId;
    //班级编号
    private String gradeId;
    //考试名称
    private String examName;
    //考试时间
    private String examTime;
    //全体评论
    private String comment;
    //发送状态
    private Integer status;
    //发布时间
    private String createTime;
    //学生学号
    private List<StuNumber>numbers;
    //科目信息
    private List<Subject>subjects;
    //学生评论
    private List<StuComment>stuComments;
    //班级信息
    private Class_add class_add;

    public Class_add getClass_add() {
        return class_add;
    }

    public void setClass_add(Class_add class_add) {
        this.class_add = class_add;
    }

    public List<StuComment> getStuComments() {
        return stuComments;
    }

    public void setStuComments(List<StuComment> stuComments) {
        this.stuComments = stuComments;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<StuNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<StuNumber> numbers) {
        this.numbers = numbers;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
