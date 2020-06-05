package com.zlx.crud.service;

import com.zlx.crud.entity.Life;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LifeService {
    public List<Life> getLife(@Param("id") Integer id);
}
