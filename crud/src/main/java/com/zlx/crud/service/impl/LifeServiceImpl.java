package com.zlx.crud.service.impl;

import com.zlx.crud.dao.LifeDao;
import com.zlx.crud.entity.Life;
import com.zlx.crud.service.LifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeServiceImpl implements LifeService {
    @Autowired
    private LifeDao dao;

    @Override
    public List<Life> getLife(Integer id) {
        return dao.getLife(id);
    }
}
