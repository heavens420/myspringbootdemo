package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.repository.UserCrudRepository;
import com.zlx.datajpa.repository.UserReopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ExampleMatcher方式查询
 *
 * 方法名	        作用
 * ignoreCase	        与字符串不区分大小写的匹配
 * caseSensitive	    与字符串区分大小写的匹配
 * contains	            与字符串模糊匹配，%{str}%
 * endsWith	            与字符串模糊匹配,%{str}
 * startsWith	        与字符串模糊匹配,{str}%
 * exact	            与字符串精确匹配
 * storeDefaultMatching	默认匹配模式
 * regex	            将字符串视为正则表达式进行匹配
 */
@Service
public class UserService {
    @Autowired
    private UserReopsitory userReopsitory;

    /**
     * 当字段为null时 默认不会作为条件 否则必须手动指定忽略字段
     * @param user
     * @return
     */
    public List<User> getAll(User user){
//        user.setPhone("110");

        ExampleMatcher matcher = ExampleMatcher.matching()
                // name like %name%
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                // id = xxx
                .withMatcher("id",ExampleMatcher.GenericPropertyMatchers.exact())
                // 忽略passwd字段，不作为查询条件
                .withIgnorePaths("passwd")
                // 忽略空字段
                .withIgnoreNullValues();

        final Example<User> options = Example.of(user,matcher);
        List<User> list = userReopsitory.findAll(options);
        return list;
    }
}
