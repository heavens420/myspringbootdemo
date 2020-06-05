package com.zlx.jjwt.controller;

import com.zlx.jjwt.bean.User;
import com.zlx.jjwt.service.UserService;
import com.zlx.jjwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserService service;

    @RequestMapping("login")
    public String login(String userName,String passwd,HttpServletRequest request){
        User user = service.getUserById(userName);
        System.out.println(user.getPassword());
        if (user == null || !user.getPassword().equals(passwd)){
            return "用户名或密码错误";
        }else {
            //登录成功
            Map<String,Object> map = new HashMap<>();
            map.put("age",user.getAge());
            map.put("phone",user.getPhone());
            String token = new JwtUtils().createJwtToken(user.getId(), user.getName(), map);

            //登录成功后 前端获取到token 将其加入请求头
            return token;
        }
    }

    //
    @RequestMapping("u")
    public User getUserByName(String userName){
        return service.getUserById(userName);
    }

    /**
     * 模拟获取前端请求头中的token并 解析token获取用户信息
     * @param request
     * @return
     */
    @RequestMapping("header")
    public Map<String,Object> getHeaderMessage(HttpServletRequest request){
        String header = request.getHeader("Aheader");
        Map<String,Object> map = new HashMap<>();

        if (header == null || header == ""){
            map.put("message","header is null");
            return map;
        }else {
            String token = header.replace("Bearer ","");
            Claims claims = new JwtUtils().parseToken(token);
            String id = claims.getId();
            map.put("message","可根据用户id操作数据,比如封装用户信息包含角色和权限并返回给前端");
            map.put("id",id);
            return map;
        }
    }
}
