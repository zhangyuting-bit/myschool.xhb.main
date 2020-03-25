package com.zb.entity;

import java.io.Serializable;
//表情类
public class Expression implements Serializable {
    //表情编号
    private Integer expressionId;
    //表情路径
    private String expressionSrc;

    public Integer getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(Integer expressionId) {
        this.expressionId = expressionId;
    }

    public String getExpressionSrc() {
        return expressionSrc;
    }

    public void setExpressionSrc(String expressionSrc) {
        this.expressionSrc = expressionSrc;
    }
}
