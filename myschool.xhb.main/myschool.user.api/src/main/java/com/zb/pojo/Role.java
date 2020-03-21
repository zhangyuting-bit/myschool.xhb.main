package com.zb.pojo;

import java.io.Serializable;

public class Role implements Serializable {
    private String id;
    private String role_name;
    private String role_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role_code) {
        this.role_code = role_code;
    }

    public Role() {
    }

    public Role(String id, String role_name, String role_code) {
        this.id = id;
        this.role_name = role_name;
        this.role_code = role_code;
    }
}
