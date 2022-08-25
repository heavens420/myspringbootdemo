package com.zlx.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "t_person")
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String psersonName;

    //遵照jpa 规范 一对多 外键由多端维护 多端实现增删改查
//    @ManyToOne
//    @JoinColumn(name = "id" ,referencedColumnName = "id",insertable = false,updatable = false)
//    @JsonBackReference  // 解决 返回结果嵌套循环
//    private Role role;

}
