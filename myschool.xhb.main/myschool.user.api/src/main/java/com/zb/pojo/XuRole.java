package com.zb.pojo;

import java.io.Serializable;

public class XuRole implements Serializable {
    private String id;
    private String user_id;
    private String role_id;
    private String create_time;
    private String update_time;
    private String creator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public XuRole() {
    }

    public XuRole(String id, String user_id, String role_id, String create_time, String update_time, String creator) {
        this.id = id;
        this.user_id = user_id;
        this.role_id = role_id;
        this.create_time = create_time;
        this.update_time = update_time;
        this.creator = creator;
    }
}
