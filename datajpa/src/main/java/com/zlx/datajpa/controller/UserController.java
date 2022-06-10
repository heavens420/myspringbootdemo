package com.zlx.datajpa.controller;

import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.service.UserJpaRepositoryService;
import com.zlx.datajpa.service.UserPagingAndSortingService;
import com.zlx.datajpa.service.UserCrudRepositoryService;
import com.zlx.datajpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserCrudRepositoryService service;

    @Autowired
    private UserPagingAndSortingService pagingAndSortingService;

    @Autowired
    private UserJpaRepositoryService jpaRepositoryService;

    @Autowired
    private UserService userService;


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
    public List<User> FindAll(int page, int size, @RequestBody User user){
        System.out.println(user);
         return jpaRepositoryService.findAllByPagingAndSorting(page, size, user).get().collect(Collectors.toList());
    }

    @GetMapping("/user")
    public Map<String, Object> findUsersByLikeName(int page,int size,String name){
        Pageable pageable = PageRequest.of(page - 1, size);
        Map<String, Object> map = new HashMap<>(8);
        final Page<User> user = jpaRepositoryService.findUserByNameLike(pageable, name);
        map.put("user", user.getContent());
        return map;
    }

    @GetMapping("/sub")
    public Map<String, Object> findUserBySubQuery(int page,int size,String name,String address){
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> map = new HashMap(8);
        final Page<User> subQuery = jpaRepositoryService.findUserBySubQuery(pageable, name, address);
        map.put("user",subQuery);
        return map;
    }

    @GetMapping("/ur")
    public Map<String, Object> findUserByJoin(int page,int size,int userId){
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> map = new HashMap<>(8);
        final Page<User> userJoinRole = jpaRepositoryService.findUserJoinRole(pageable, userId);
        map.put("user-role", userJoinRole);
        return map;
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
        List<User> users = jpaRepositoryService.findNameLike(name,id);
        Map<String,Object> map = new HashMap();
        map.put("user",users);
        return map;
    }

    @RequestMapping("/12")
    public Map<String,Object> FindAddrLike(String addr,int id){
        List<User> users = jpaRepositoryService.findAddrLike(addr,id);
        Map<String,Object> map = new HashMap();
        map.put("user",users);
        return map;
    }

    @RequestMapping("/13")
    public Map<String,Object> FindAddrIS(String addr){
        List<User> users = jpaRepositoryService.findAddrIS(addr);
        Map<String,Object> map = new HashMap();
        map.put("user",users);
        return map;
    }

    @RequestMapping("/14")
    public Map<String,Object> UpdateUserByid(String addr,String name,int id){
        jpaRepositoryService.updateUserById(addr,name,id);
        Map<String,Object> map = new HashMap();
        map.put("result","修改成功");
        return map;
    }

    @GetMapping("/matcher")
    public Map<String, Object> getAllUser(){
        User user = new User();
        user.setId(1);
//        user.setAddr("北京");
        user.setPasswd("333333");
        user.setName("zs");
        final List<User> all = userService.getAll(user);
        Map<String, Object> map = new HashMap(4);
        map.put("result", all);
        return map;
    }
}
