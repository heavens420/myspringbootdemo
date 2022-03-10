package com.zlx.crud.config;

import com.zlx.crud.entity.mongodb.User;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<User>  list = new ArrayList<>();
        list.add(new User(12,"nihao",10,"hhhh","1222"));
        list.add(new User(12,"ni",10,"hhhh","22"));
        list.add(new User(12,"hao",10,"hhhh","33"));

//        list.forEach(user -> user.setPasswd(MD5.md5(user.getUserId()+"",user.getPasswd())));

//        list.forEach(user -> System.out.println(user.getPasswd()));
    }
}
