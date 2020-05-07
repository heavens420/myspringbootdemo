package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.Student;
import com.zlx.datajpa.repository.StudentRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student Add(Student student){
        return repository.save(student);
    }

    public Student find(int id){
        return repository.findById(id).get();
    }

    public void DeleteStudent(int id){
        repository.deleteById(id);
    }
}
