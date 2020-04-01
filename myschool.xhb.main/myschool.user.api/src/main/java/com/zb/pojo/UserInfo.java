package com.zb.pojo;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String id;
    private String username;
    private String password;
    private String name;
    private String schoolname;
    private String userpic;
    private Integer codeType;
    private String phone;
    private String status;
    private String create_time;
    private String update_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public UserInfo() {
    }

    public UserInfo(String id, String username, String password, String name, String schoolname, String userpic, Integer codeType, String phone, String status, String create_time, String update_time) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.schoolname = schoolname;
        this.userpic = userpic;
        this.codeType = codeType;
        this.phone = phone;
        this.status = status;
        this.create_time = create_time;
        this.update_time = update_time;
    }
}
