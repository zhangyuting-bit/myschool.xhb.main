package com.zb.pojo;

import java.io.Serializable;

public class Class_real implements Serializable {
    private Integer id;
    //学历的id
    private Integer real_id;
    //学历名称
    private String education;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReal_id() {
        return real_id;
    }

    public void setReal_id(Integer real_id) {
        this.real_id = real_id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
