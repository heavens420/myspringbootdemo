package com.zlx.crud.service;



import com.zlx.crud.entity.Role;
import com.zlx.crud.entity.User;
import com.zlx.crud.entity.UserRole;

import java.util.List;

public interface UserRoleService {

    public UserRole insertUserRole(User user, Role role);

    public UserRole updateUserRole(String name, int id);

    public boolean deleteUserRole(int id);

    public boolean batchDeleteUserRole(List<Integer> list);
}
