package com.zlx.datajpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "t_student")
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String studentName;

    @OneToOne(cascade = CascadeType.ALL)
    //外键
    //grade_id 参考 grade表中的 id
    @JoinColumn(name = "grade_id",referencedColumnName = "id")
    private Grade grade;

    public Student(String studentName){
        this.studentName = studentName;
    }
}
