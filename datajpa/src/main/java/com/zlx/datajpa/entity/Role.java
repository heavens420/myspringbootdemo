package com.zlx.datajpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "t_role")
@Getter
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;
    private int userId;

//    @OneToMany(fetch=FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REFRESH})
//    @JoinColumn(name = "id", referencedColumnName = "id")
//    private Set<Person> personList;

}
