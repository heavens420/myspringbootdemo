package com.zlx.crud.dao;

import com.zlx.crud.entity.mysql.Role;
import com.zlx.crud.entity.mysql.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRoleDao {
    //插入数据到中间表
    public int insertUserRole(User user, Role role);

    public int updateUserRole(String name, int id);

    /**
     * 根据用户id修改用户角色
     * @param map
     * @return
     */
    public int updateUserAndRole(Map<Object,Integer> map);

    //删除中间表
    public int deleteUserRole(@Param("userId") int id);

    //批量删除中间表
    public int batchDeleteUserRole(List<Integer> list);
}
