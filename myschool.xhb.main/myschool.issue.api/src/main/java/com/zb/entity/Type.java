package com.zb.entity;

import java.io.Serializable;
//通知,习惯,讨论,作业,活动等类型类
public class Type implements Serializable {
    //类型编号
    private Integer typeId;
    //类型名称
    private String typeName;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
