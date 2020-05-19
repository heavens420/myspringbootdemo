package com.zlx.crud.controller;

import com.zlx.crud.entity.User;
import com.zlx.crud.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (User)表控制层
 *
 * @author zhaolonglong
 * @since 2020-01-08 09:25:59
 */

@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "selectOne",produces = {"application/json;charset=utf-8"})
    public User selectOne(Integer id) {
        User user = this.userService.queryById(1);
        System.out.println(user);
        System.out.println("test");
        return user;
    }

    @RequestMapping("addOne")
    public int addOne(){
        User user = new User();
        user.setAddr("九华山");
        user.setAge(21);
        user.setPhone("3425379275");
        user.setName("little");
        User us = this.userService.insert(user);
        return 0;
    }

    @GetMapping(value = "queryAll")
    public List<User> queryAll(Model model){
        List<User> list = this.userService.queryAll();
        list.stream().forEach(user -> System.out.println(user.toString()));
//        model.addAttribute("list",list);
//        return "queryList";

        //出现空指针异常
        String s = null;
        s.length();
        return list;
    }

    @GetMapping("deleteUserRoleByUserId")
    public boolean deleteUSerRole(){
        int id = 8;
        return userService.deleteById(id);
    }

    @GetMapping("deleteList")
    public Boolean deleteList(){
        Map map = new HashMap();
        List list = new ArrayList(Arrays.asList(18,19,20));
//        List<Integer> list = new ArrayList();
//        list.add(4);
//        list.add(5);
//        list.add(7);
        map.put("item",list);
        System.out.println("begin foreach");
        list.stream().forEach(System.out::print);
        return this.userService.deleteList(list);
    }

    @GetMapping("age")
    public Long getCount(@RequestParam(defaultValue = "23") int age){
        if (userService.countAge(age) == null){
            return 0L;
        }
        return userService.countAge(age);
    }

    @GetMapping("total")
    public long getTotal(){
        return userService.countTotal();
    }

    @RequestMapping("time")
    public List<User> getTime(){

        List<User> users =  userService.getTime();
        users.stream().forEach(user -> System.out.println(user));
        return users;
    }

    @RequestMapping("date")
    public List<User> queryByTime( Date startTime,
                                   Date endTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        startTime = new Date();
        endTime = new Date();
        System.out.println(startTime+"::::"+endTime);
        return userService.queryByTime(startTime,endTime);
    }


}