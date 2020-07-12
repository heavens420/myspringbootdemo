package com.zlx.crud.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface PermissionDao {
    /**
     * 根据当前登录的用户id查询其角色拥有的权限
     * @param userId
     * @return
     */
    Set<String> queryPermissioinByUserId(Integer userId);
}
