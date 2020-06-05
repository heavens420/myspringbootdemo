package com.zlx.crud.dao;

import com.zlx.crud.entity.Life;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LifeDao {
    public List<Life> getLife(Integer id);
}
