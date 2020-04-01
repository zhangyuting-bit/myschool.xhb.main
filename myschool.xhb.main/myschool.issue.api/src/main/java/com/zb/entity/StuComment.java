package com.zb.entity;

import java.io.Serializable;
//老师给学生的评论表
public class StuComment implements Serializable {
    ///评论编号
    private String commentId;
    //成绩通知编号
    private String scoreId;
    //学员学号编号
    private String numberId;
    //学生评论
    private String comment;

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
