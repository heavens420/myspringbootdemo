package com.zlx.security.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 security 登录 默认用户名 user 密码见控制台
 */
@RestController
public class First {

    @RequestMapping("/admin/1")
    public String One(){
        return "hello security";
    }

    @RequestMapping("/user/2")
    public String Two(){
        return "This is User ";
    }
}
