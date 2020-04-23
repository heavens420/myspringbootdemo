package com.zlx.crud.controller;

//test
import com.zlx.crud.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin
@RestController
public class TestController {


    @GetMapping("/1")
    public Map<String,Object> GetUser(String a,int b){
        Map<String,Object> map = new HashMap();
        List list = new ArrayList();
        User user1 = new User(12, 22, "mao");
        User user2 = new User(13, 24, "xi");
        list.add(user1);
        list.add(user2);
        map.put("users",list);
        System.out.println("a= "+a);
        System.out.println("b= "+b);
        return map;
    }

    @PostMapping("2")
    public List<User> getAll(@RequestBody User user){
        List list = new ArrayList();
        User user1 = new User(12, 22, "mao");
        User user2 = new User(13, 24, "xi");
        list.add(user1);
        list.add(user2);
        System.out.println(user);
//        System.out.println(us);
        return list;
    }

    @PostMapping("3")
    public List<User> receiveUsers(@RequestBody List<User> list){
       list.stream().forEach(user -> System.out.println(user));
        return list;
    }
}
