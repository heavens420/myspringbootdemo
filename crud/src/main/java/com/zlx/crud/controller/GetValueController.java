package com.zlx.crud.controller;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConfigurationProperties(prefix = "myvalue")
@Data
@RestController
@RequestMapping("/test")
public class GetValueController {

    private String key;
    private String value;

    @Value("${anothervalue.value}")
    private String anotherValue;

    @RequestMapping("/value")
    public String printValue(){
        return key + value + "ceshi"+anotherValue;
    }
}
