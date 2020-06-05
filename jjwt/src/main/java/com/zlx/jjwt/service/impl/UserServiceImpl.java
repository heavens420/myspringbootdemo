package com.zlx.jjwt.service.impl;

import com.zlx.jjwt.bean.User;
import com.zlx.jjwt.dao.UserDao;
import com.zlx.jjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao dao;

    @Override
    public User getUserById(String userId) {
        return dao.getUserById(userId);
    }
}
