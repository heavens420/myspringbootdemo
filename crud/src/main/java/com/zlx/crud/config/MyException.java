package com.zlx.crud.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //用于自定义异常
public class MyException {
    @ResponseBody  //返回json字符串
    @ExceptionHandler(value = NullPointerException.class) //捕捉异常类型
    public Map<String,Object> NullException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("code",500);
        map.put("msg","空指针");
        return map;
    }

//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public Map<String,Object> Exception(Exception e){
//        Map map = new HashMap();
//        map.put("code","999");
//        map.put("message","系统繁忙");
//        return map;
//    }
}
