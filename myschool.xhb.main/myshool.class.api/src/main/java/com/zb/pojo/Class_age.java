package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;

/**
*   pojo of class_age
*/
public class Class_age implements Serializable {
    //
    private Integer id;
    //年纪编号
    private Integer age_id;
    //年纪名称
    private String age_name;
    //getter setter
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setAge_id (Integer  age_id){
        this.age_id=age_id;
    }
    public  Integer getAge_id(){
        return this.age_id;
    }
    public void setAge_name (String  age_name){
        this.age_name=age_name;
    }
    public  String getAge_name(){
        return this.age_name;
    }
}
