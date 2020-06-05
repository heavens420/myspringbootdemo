package com.zlx.jjwt.dao;

import com.zlx.jjwt.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("<script> select * from user where name = <if test = \"name != null and name != ''\">#{name}</if></script>")
    public User getUserById(@Param("name") String userId);
}
