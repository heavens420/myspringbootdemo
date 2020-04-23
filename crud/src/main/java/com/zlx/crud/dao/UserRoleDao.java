package com.zlx.crud.dao;

import com.zlx.crud.entity.Role;
import com.zlx.crud.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleDao {
    //插入数据到中间表
    public int insertUserRole(User user, Role role);

    public int updateUserRole(String name, int id);

    //删除中间表
    public int deleteUserRole(@Param("userId") int id);

    //批量删除中间表
    public int batchDeleteUserRole(List<Integer> list);
}
