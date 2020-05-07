package com.zlx.security.auth;

import com.sun.org.glassfish.gmbal.ManagedData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface MyUserDetailsMapper {

    //查询用户信息根据用户名
    @Select("SELECT username,password,enabled FROM sys_user u " +
            "WHERE u.username= #{userId} or u.phone = #{userId} ")
    MyUserDetails findByUserName(@Param("userId") String userId);

    //查询角色列表根据用户名
    @Select("SELECT role_code from sys_role r \n" +
            "LEFT JOIN sys_user_role ur ON r.id = ur.role_id \n" +
            "LEFT JOIN sys_user u ON ur.user_id = u.id \n " +
            "WHERE u.username = #{userId} or u.phone = #{userId}")
    List<String> findRoleByUserName(@Param("userId") String userId);

    //查询角色权限列表根据角色列表
    @Select({
            "<script>",
                "SELECT url FROM sys_role r " ,
                        "LEFT JOIN sys_role_menu rm ON r.id = rm.role_id " ,
                        "LEFT JOIN sys_menu m ON m.id = rm.menu_id " ,
                        "WHERE r.role_code IN ",
                    "<foreach collection = 'roleCodes' item = 'roleCode' open = '(' separator = ',' close = ')' > ",
                        "#{roleCode}",
                    "</foreach>",
            "</script>"
    })
    List<String> findAuthorityByRoleCode(@Param("roleCodes") List<String> roleCodes);
}
