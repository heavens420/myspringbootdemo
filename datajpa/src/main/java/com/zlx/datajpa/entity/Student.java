package com.zlx.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonView;
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
    private String studentPasswd;

    @OneToOne(cascade = CascadeType.ALL)
    //外键
    //grade_id 参考 grade表中的 id
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grade grade;


    //定义视图 用于自定义查询要展示的字段
    //简单视图（不包含密码）
    public interface UserSimpleView {
    }

    //详细视图（包含密码）  继承简单视图 则包含简单视图中的所有字段
    public interface UserDetailView extends UserSimpleView {
    }

    public Student(String studentName) {
        this.studentName = studentName;
    }

    //controller 方法上 也加上此注解 则该方法查询可显示密码 不加此注解的方法不会再出现密码字段
    @JsonView(UserDetailView.class)
    public String getStudentPasswd() {
        return studentPasswd;
    }

    //对于封装的数据无效 故注解对Grade失效
    @JsonView(UserSimpleView.class)
    public Grade getGrade() {
        return grade;
    }
}
