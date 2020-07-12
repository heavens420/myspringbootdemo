package com.zlx.crud.service;



import com.zlx.crud.entity.mysql.Role;
import com.zlx.crud.entity.mysql.User;
import com.zlx.crud.entity.mysql.UserRole;

import java.util.List;

public interface UserRoleService {

    public UserRole insertUserRole(User user, Role role);

    public UserRole updateUserRole(String name, int id);

    /**
     * 根据用户id修改用户角色
     * @param userId,roleId
     * @return
     */
    public int updateUserAndRole(Integer userId,Integer id);

    public boolean deleteUserRole(int id);

    public boolean batchDeleteUserRole(List<Integer> list);
}
