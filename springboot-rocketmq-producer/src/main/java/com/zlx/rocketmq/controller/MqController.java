package com.zlx.rocketmq.controller;

import com.zlx.rocketmq.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MqController {

    @Autowired
    private MqService service;

    @GetMapping("/strmsg")
    public void sendStringMessage(){
        service.sendStringMessage("topic-springboot","nihaoa");
    }

    @GetMapping("/oneway")
    public void sendOneWayMessage(){
        service.SendOneWayMessage("one-way","one-way-message");
    }
}
