package com.zlx.crud.controller;

import com.zlx.crud.entity.mysql.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Login {

    @RequestMapping("/")
    public String login(User user, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(), user.getPasswd());
        try {
            subject.login(usernamePasswordToken);
            return "登录成功";
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            return "用户名或密码错误";
        }
    }
}
