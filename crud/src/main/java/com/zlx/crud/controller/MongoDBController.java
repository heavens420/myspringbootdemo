package com.zlx.crud.controller;

import com.zlx.crud.entity.mongodb.User;
import com.zlx.crud.service.MongoService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mongo")
public class MongoDBController {

    @Resource
    private MongoService mongoService;

    @PostMapping("add")
    public User addUser(User user){
//        User mongoUser = new User(1,"xijinping",20,"男","888");
        return mongoService.addUser(user);
    }

    @PostMapping("batchAdd")
    public List<User> batchAddUser(@RequestBody List<User> userList){
//        List<User> userList = new ArrayList<>();
//        userList.add(new User(2, "川普", 21, "男", "999"));
//        userList.add(new User(3, "希拉里", 33, "女", "998"));
//        userList.add(new User(4, "拜登", 42, "男", "997"));
        try {
            return mongoService.batchAddUser(userList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/del")
    public int deleteUser(Integer userId){
        try {
            mongoService.deleteUser(userId);
            return 1;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @PutMapping("put")
    public User updateUser(User user){
        return mongoService.updateUser(user);
    }

    @GetMapping("get")
    public User findUserById(Integer  userId){
        return mongoService.findUser(userId);
    }

    @GetMapping("getAll")
    public List<User> findAllUser(){
        return mongoService.findAllUser();
    }

    @GetMapping("regex")
    public List<User> getUserByRegex(String name){
        return mongoService.getUserByRegex(name);
    }

    @GetMapping("/as")
    public List<User> getUserByAgeAndSex(int age, String sex){
        return mongoService.getUsers(age,sex);
    }
}
