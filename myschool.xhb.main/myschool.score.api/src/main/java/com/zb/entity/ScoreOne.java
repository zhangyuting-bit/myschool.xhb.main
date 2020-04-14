package com.zb.entity;

import java.io.Serializable;

//分数用户表
public class ScoreOne implements Serializable {
    ///分数用户编号
    private String oneId;
    //分数编号
    private String scoreId;
    //用户编号
    private String userId;
    //创建时间
    private String createTime;

    public String getOneId() {
        return oneId;
    }

    public void setOneId(String oneId) {
        this.oneId = oneId;
    }

    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
