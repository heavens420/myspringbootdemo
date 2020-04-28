package com.zlx.datajpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "t_role")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;

    @OneToMany(mappedBy = "role",cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private List<Person> personList;
}
