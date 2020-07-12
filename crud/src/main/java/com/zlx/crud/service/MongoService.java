package com.zlx.crud.service;

import com.zlx.crud.entity.mongodb.User;

import java.util.List;

public interface MongoService {

    /**
     * 新增单个用户
     * @param user
     * @return
     */
    User addUser(User user);

    /**
     * 批量增加用户
     * @param list
     * @return
     */
    List<User> batchAddUser(List<User> list);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int deleteUser(Integer userId);

    /**
     * 更新用户
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 条件查询用户
     * @param id
     * @return
     */
    User findUser(Integer userId);

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAllUser();
}
