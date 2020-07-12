package com.zlx.crud.controller;

import com.zlx.crud.entity.mysql.Life;
import com.zlx.crud.service.LifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LifeController {

    @Autowired
    private LifeService service;

    @RequestMapping("life")
    public Map<String,Object> getLife(Integer id){
        Map<String,Object> map = new HashMap<>();
        map.put("message","查询结果");
        List<Life> life = service.getLife(id);
        map.put("life",life);
        return map;
    }
}
