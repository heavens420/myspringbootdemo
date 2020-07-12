package com.zlx.crud.controller;


import com.zlx.crud.entity.mysql.Role;
import com.zlx.crud.entity.mysql.User;
import com.zlx.crud.service.UserRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("userRole")
public class UserRoleController {
    @Resource
    public UserRoleService userRoleService;

    @RequestMapping(value = "ur",produces = {"application/json;charset=utf-8"})
    public int insertUserRole(){
        User user = new User(55,"qqp","803","China");
        Role role = new Role();
        role.setId(2);
        System.out.println("111111111");
        userRoleService.insertUserRole(user,role);
        System.out.println("222222222");
        return 0;
    }

    @RequestMapping("updateur")
    public int updateUserRole(){
        userRoleService.updateUserRole("唐僧",1);
        return 0;
    }

    @RequestMapping("/up")
    public int updateUserRole2(Integer userId,Integer id){
        return userRoleService.updateUserAndRole(userId,id);
    }
}
