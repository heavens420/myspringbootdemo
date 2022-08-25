package com.zlx.datajpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "t_grade")
@Data
@NoArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String gradeName;

    private int userId;

//    @OneToOne(mappedBy = "grade")//外键由Student中的grade维护
//    private Student student;

    public Grade(String gradeName) {
        this.gradeName = gradeName;
    }

}
