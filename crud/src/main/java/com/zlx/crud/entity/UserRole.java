package com.zlx.crud.entity;

import java.io.Serializable;

public class UserRole implements Serializable {
    private Integer id;
    private Integer roleId;
    private Integer userIdp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserIdp() {
        return userIdp;
    }

    public void setUserIdp(Integer userIdp) {
        this.userIdp = userIdp;
    }
}
