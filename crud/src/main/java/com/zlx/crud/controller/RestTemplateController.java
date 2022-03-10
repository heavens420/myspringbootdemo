package com.zlx.crud.controller;


import com.zlx.crud.entity.RegionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestTemplateController {

    @Resource
    private RestTemplate restTemplate;

    final String URL = "http://localhost:8082/cloud-manage/cmp-res-region/region?cloudId={1}";

    @GetMapping("/region")
    public ResponseEntity<Object> sendGetWithoutParam(String value){
        Map map = new HashMap(4);
        map.put("1", value);
        final ResponseEntity entity = restTemplate.getForEntity(URL, RegionEntity.class,map);
        return entity;
    }
}
