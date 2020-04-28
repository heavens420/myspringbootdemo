package com.zlx.datajpa.controller;

import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.service.UserJpaRepositoryService;
import com.zlx.datajpa.service.UserPagingAndSortingService;
import com.zlx.datajpa.service.UserCrudRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserCrudRepositoryService service;

    @Autowired
    private UserPagingAndSortingService pagingAndSortingService;

    @Autowired
    private UserJpaRepositoryService jpaRepositoryService;

    @RequestMapping("/1")
    public Map<String,Object> add(User user){
//        User user = new User( "法外狂徒李四", "故宫紫金阁", "110", "888");
        User user1 = service.add(user);
        Map<String, Object> map = new HashMap<>();
        map.put("user",user1);
        return map;
    }

    @RequestMapping("/2")
    public Map<String,Object> update(){
        User user = new User(12, "法外狂徒张三", "北京中南海", "911", "888");
        User user1 = service.add(user);
        Map<String, Object> map = new HashMap<>();
        map.put("user",user1);
        return map;
    }

    @RequestMapping("/3")
    public Map<String,Object> FindById(int id){
        User user = service.FindUserById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("user",user);
        return map;
    }

    @RequestMapping("/4")
    public Map<String,Object> FindAll(){
        Iterable<User> users = service.FindUserAll();
        Map<String, Object> map = new HashMap<>();
        map.put("user",users);
        System.out.println("map: "+map);
        List<User> userList = (List<User>) users;
        System.out.println("user: "+userList);
        return map;
    }

    @RequestMapping("d/{id}")
    public String  DeleteById(@PathVariable int id){
        service.DeleteById(id);
        return "删除成功";
    }

    @RequestMapping("/5")
    public long CountUser(){
        return service.GetUserCount();
    }

    //排序
    @RequestMapping("/6")
    public Map<String ,Object> FindAllBySort(){
        Iterable<User> users = pagingAndSortingService.FindAllBySort();
        Map<String,Object> map = new HashMap<>();
        map.put("users",users);
        return map;
    }

    @RequestMapping("/7")
    public Map<String ,Object> FindAllBySortAndPage(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        Iterable<User> users = pagingAndSortingService.FindAllByPageAndSort(page-1,size);
        Map<String,Object> map = new HashMap<>();
        map.put("users",users);
        return map;
    }

    @RequestMapping("/8")
    public Page<User> FindAll(int page, int size, @RequestBody User user){
        System.out.println(user);
        return jpaRepositoryService.FindAllByPagingAndSorting(page, size, user);
    }

    @RequestMapping("/9")
    public Map<String,Object> FindUserByName(String name){
        User user = jpaRepositoryService.findUserByName(name);
        Map<String,Object> map = new HashMap();
        map.put("user",user);
        return map;
    }

    @RequestMapping("/10")
    public Map<String,Object> FindUserByNameLike(String name){
        List<User> users = jpaRepositoryService.findUsersByNameIsLike(name);
        Map<String,Object> map = new HashMap();
        map.put("user",users);
        return map;
    }

    @RequestMapping("/11")
    public Map<String,Object> FindNameLike(String name,int id){
        List<User> users = jpaRepositoryService.FindNameLike(name,id);
        Map<String,Object> map = new HashMap();
        map.put("user",users);
        return map;
    }

    @RequestMapping("/12")
    public Map<String,Object> FindAddrLike(String addr,int id){
        List<User> users = jpaRepositoryService.FindAddrLike(addr,id);
        Map<String,Object> map = new HashMap();
        map.put("user",users);
        return map;
    }

    @RequestMapping("/13")
    public Map<String,Object> FindAddrIS(String addr){
        List<User> users = jpaRepositoryService.FindAddrIS(addr);
        Map<String,Object> map = new HashMap();
        map.put("user",users);
        return map;
    }

    @RequestMapping("/14")
    public Map<String,Object> UpdateUserByid(String addr,String name,int id){
        jpaRepositoryService.UpdateUserById(addr,name,id);
        Map<String,Object> map = new HashMap();
        map.put("result","修改成功");
        return map;
    }
}
