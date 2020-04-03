package com.zb.entity;

import java.io.Serializable;
import java.util.List;

//老师给学生的评论表
public class StuComment implements Serializable {
    //评论编号
    private String commentId;
    //成绩通知编号
    private String scoreId;
    //学员学号编号
    private String numberId;
    //学生评论
    private String comment;
    //总分
    private Double sum;
    //排名
    private Integer sort;
    //学生
    private StuNumber number;
    //科目
    private List<Subject>subjects;

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public StuNumber getNumber() {
        return number;
    }

    public void setNumber(StuNumber number) {
        this.number = number;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
