package com.zb.pojo;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
*   pojo of class_age_real
*/
public class Class_age_real implements Serializable {
    //
    private Integer id;
    //学历的id
    private Integer real_id;
    //年纪的id
    private Integer age_id;
    private String age_name;
    private Integer ageid;
    //getter setter
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setReal_id (Integer  real_id){
        this.real_id=real_id;
    }
    public  Integer getReal_id(){
        return this.real_id;
    }
    public void setAge_id (Integer  age_id){
        this.age_id=age_id;
    }
    public  Integer getAge_id(){
        return this.age_id;
    }

    public String getAge_name() {
        return age_name;
    }

    public void setAge_name(String age_name) {
        this.age_name = age_name;
    }

    public Integer getAgeid() {
        return ageid;
    }

    public void setAgeid(Integer ageid) {
        this.ageid = ageid;
    }
}
