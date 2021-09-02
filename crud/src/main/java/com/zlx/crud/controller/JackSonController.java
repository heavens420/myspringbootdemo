package com.zlx.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlx.crud.entity.redis.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/jackson")
public class JackSonController {

    @Autowired
    ObjectMapper mapper;

    /**
     * Jackson 序列化  对象 --> String
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/ser")
    public String serialization() throws JsonProcessingException {
        User user = new User();
        user.setId(1);
        user.setName("zhangsan");
        user.setBirthday(new Date());

        return mapper.writeValueAsString(user);
    }

    /**
     * 绑定json到对象
     * @return
     * @throws JsonProcessingException
     */
    @GetMapping("/rjo")
    public String readJsonAsObject() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"zhang\"}";
        final User user = mapper.readValue(json, User.class);
        return  user.getName()+user.getId();
    }
}
