package com.zlx.crud.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 357961073544118289L;

    private String id;
    private String permissionCode;
    private String permissionName;

}
