package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;

/**
*   pojo of jurisdiction
*/
public class Jurisdiction implements Serializable {
    //
    private Integer id;
    //班级内部的专属职位
    private String jurisdiction_name;
    //getter setter
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setJurisdiction_name (String  jurisdiction_name){
        this.jurisdiction_name=jurisdiction_name;
    }
    public  String getJurisdiction_name(){
        return this.jurisdiction_name;
    }
}
