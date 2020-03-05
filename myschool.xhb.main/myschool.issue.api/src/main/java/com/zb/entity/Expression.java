package com.zb.entity;

import java.io.Serializable;

public class Expression implements Serializable {
    private Integer expressionId;
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
