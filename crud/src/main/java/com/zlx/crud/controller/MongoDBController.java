package com.zlx.crud.controller;

import com.zlx.crud.entity.mongodb.User;
import com.zlx.crud.service.MongoService;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
}
