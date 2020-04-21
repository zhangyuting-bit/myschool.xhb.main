package com.zb.pojo;

import java.io.Serializable;

public class Class_Jobinfo implements Serializable {
    private Integer number;  //学生 或老师的编号
    private String call_name;  //加入的名称
    private Integer class_number;  //加入的班级号

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCall_name() {
        return call_name;
    }

    public void setCall_name(String call_name) {
        this.call_name = call_name;
    }

    public Integer getClass_number() {
        return class_number;
    }

    public void setClass_number(Integer class_number) {
        this.class_number = class_number;
    }
}
