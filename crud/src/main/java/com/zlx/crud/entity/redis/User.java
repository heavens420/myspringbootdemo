package com.zlx.crud.entity.redis;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer id;
    private String name;
    private Date birthday;

}
