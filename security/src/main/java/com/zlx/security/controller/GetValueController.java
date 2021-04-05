package com.zlx.security.controller;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConfigurationProperties(prefix = "myvalue")
@Data
@RestController
@RequestMapping("/")
public class GetValueController {

    private String key;
    private String value;

    @RequestMapping("/vv")
    public String printValue(){
        return key + value + "ceshi";
    }
}
