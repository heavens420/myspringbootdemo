package com.zlx.crud.service.impl;

import com.zlx.crud.dao.TestDao;
import com.zlx.crud.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Zhao LongLong
 * @Date 2020/9/25
 * @Version 1.0
 * @Desc
 */

@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao dao;

    @Override
    public String getTest(String name) {
        return dao.getName(name);
    }
}
