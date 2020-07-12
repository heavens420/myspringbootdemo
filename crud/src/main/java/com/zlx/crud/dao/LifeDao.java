package com.zlx.crud.dao;

import com.zlx.crud.entity.mysql.Life;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LifeDao {
    public List<Life> getLife(Integer id);
}
