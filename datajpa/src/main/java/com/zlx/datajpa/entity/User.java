package com.zlx.datajpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "t_user")
@Data
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    private int id;
    private String name;
    private String addr;
    private String phone;
    private String passwd;

    // 不论ManyToOne还是OneToMany  name 指的是 many对象中的关联字段  referencedColumnName指的是 one对象的关联字段
    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "id", referencedColumnName = "userId", insertable = false, updatable = false)
    private Role role;

    // 不论ManyToOne还是OneToMany  name 指的是 many对象中的关联字段  referencedColumnName指的是 one对象的关联字段
    @OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "userId", referencedColumnName = "id",insertable = false, updatable= false)
    private List<Grade> gradeList = new ArrayList<>();

    public User(String name, String addr, String phone, String passwd) {
        this.name = name;
        this.addr = addr;
        this.phone = phone;
        this.passwd = passwd;
    }

    public User() {

    }

    public User(int id, String name, String addr, String phone, String passwd) {
        this.id = id;
        this.name = name;
        this.addr = addr;
        this.phone = phone;
        this.passwd = passwd;
    }
}
