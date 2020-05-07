package com.zlx.security.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyRBACSereviceMapper {

    @Select("SELECT url FROM sys_menu m \n" +
            "LEFT JOIN sys_role_menu rm ON m.id = rm.menu_id \n" +
            "LEFT JOIN sys_role r ON r.id = rm.role_id\n" +
            "LEFT JOIN sys_user_role ur ON ur.role_id = r.id\n" +
            "LEFT JOIN sys_user u ON ur.user_id = u.id\n" +
            "where u.username = #{userId}")
    List<String> findUrlByUserName(@Param("userId") String userId);

}
