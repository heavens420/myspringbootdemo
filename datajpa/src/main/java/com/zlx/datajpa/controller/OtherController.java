package com.zlx.datajpa.controller;

import com.zlx.datajpa.entity.Person;
import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.service.CopyBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/other")
public class OtherController {

    @Autowired
    private CopyBeanService copyBeanService;

    @GetMapping("/")
    public void copyBean(){
        User user = new User();
        Person person = new Person();
        person.setPsersonName("John");
        person.setId(12);
        copyBeanService.copyProperties(person, user);
        System.out.println(user);
    }
}
