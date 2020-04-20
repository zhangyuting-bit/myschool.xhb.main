package com.zb.entity;

public class XcRole {

    private String roleId;
    private Integer roleCode;
    private String roleName;
    private String createTime;
    private String updateTime;
    private String status;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public XcRole() {
    }

    public XcRole(String roleId, Integer roleCode, String roleName, String createTime, String updateTime, String status) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
    }
}
