package com.zlx.datajpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private Integer id;
    private String name;
    private String addr;
    private String phone;
    private String passwd;

    public User(String name, String addr, String phone, String passwd) {
        this.name = name;
        this.addr = addr;
        this.phone = phone;
        this.passwd = passwd;
    }
}
