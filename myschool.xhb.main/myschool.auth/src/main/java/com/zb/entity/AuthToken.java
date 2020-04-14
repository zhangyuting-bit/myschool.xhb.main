package com.zb.entity;

public class AuthToken {
    private String access_token;
    private String refresh_token;
    private String jti_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getJti_token() {
        return jti_token;
    }

    public void setJti_token(String jti_token) {
        this.jti_token = jti_token;
    }
}
