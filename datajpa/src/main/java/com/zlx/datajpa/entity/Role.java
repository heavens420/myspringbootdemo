package com.zlx.datajpa.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "t_role")
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "role",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private Set<Person> personList;

}
