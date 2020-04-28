package com.zlx.datajpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "t_person")
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String psersonName;

    //遵照jpa 规范 一对多 外键由多端维护 多端实现增删改查
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id" ,referencedColumnName = "id")
    private Role role;

}
