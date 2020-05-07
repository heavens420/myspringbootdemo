package com.zlx.datajpa.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zlx.datajpa.entity.Student;
import com.zlx.datajpa.service.StudentService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService service;

    @RequestMapping("/1")
    public Map<String,Object> Add(@RequestBody Student student){
        Map<String,Object> map = new HashMap<>();
        Student s = service.Add(student);
        map.put("student",s);
        return map;
    }

    @RequestMapping("/2")
    //查询只显示详情视图
//    @JsonView(Student.UserDetailView.class)
    public Map<String,Object> SearchById(int studentId){
        Student student = service.find(studentId);
        Map<String,Object> map = new HashMap<>();
        map.put("student",student);
        return map;
    }

    @RequestMapping("/3")
    public Map<String,Object> DeleteById(int studentId){
       service.DeleteStudent(studentId);
        Map<String,Object> map = new HashMap<>();
        map.put("student","删除成功");
        return map;
    }
}
