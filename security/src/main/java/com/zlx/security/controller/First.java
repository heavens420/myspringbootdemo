package com.zlx.security.controller;

import com.zlx.security.service.MethodELService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 security 登录 默认用户名 user 密码见控制台
 */
@RestController
public class First {

    @Autowired
    private MethodELService service;

    @RequestMapping("/user/1")
    public String One(){
        return "hello security";
    }

    @RequestMapping("/user/2")
    public String Two(){
        return "This is User ";
    }

    @RequestMapping("/sysuser")
    public String Three(){
//        return tk.zlx.interceptorannotation.service.preAu();
        return "sysuser authorthy";
    }

    @RequestMapping("/syslog")
    public String Four(){
        return "sysLog";
    }
}
