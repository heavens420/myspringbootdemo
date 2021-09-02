package tk.zlx.interceptorannotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.zlx.interceptorannotation.annontation.MyLog;
import tk.zlx.interceptorannotation.service.TokenService;

@RestController
public class TokenController {

    @Autowired
    private TokenService service;

    @GetMapping("/")
    public String test(){
        return "this is test";
    }

    @GetMapping("/token")
    public String createToken(){
        return service.createToken();
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("hello2")
    @MyLog
    public String hello2(){
        return "hello2";
    }
}
