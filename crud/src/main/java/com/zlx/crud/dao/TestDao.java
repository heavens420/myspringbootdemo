package com.zlx.crud.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Zhao LongLong
 * @Date 2020/9/25
 * @Version 1.0
 * @Desc
 */
@Mapper
public interface TestDao {
    String getName(String name);
}
