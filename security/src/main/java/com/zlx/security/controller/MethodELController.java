package com.zlx.security.controller;

import com.zlx.security.service.MethodELService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MethodELController {

    @Autowired
    private MethodELService service;

    @RequestMapping("/")
    public String preAu(){
        return service.preAu();
    }
}
