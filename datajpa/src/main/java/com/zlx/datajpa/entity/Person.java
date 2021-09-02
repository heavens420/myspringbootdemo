package com.zlx.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "t_person")
@Getter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String psersonName;

    //遵照jpa 规范 一对多 外键由多端维护 多端实现增删改查
    @ManyToOne
    @JoinColumn(name = "role_id" ,referencedColumnName = "id")
    @JsonBackReference  // 解决 返回结果嵌套循环
    private Role role;

}
