package com.zlx.crud.service;


import com.zlx.crud.entity.Life;
import com.zlx.crud.entity.User;

import java.util.Date;
import java.util.List;

/**
 * (User)表服务接口
 *
 * @author zhaolonglong
 * @since 2020-01-08 09:25:58
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 根据用户名或phone查询用户
     * @param userId
     * @return
     */
    User queryUserbyNameOrPhone(String userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查询全部数据
     *
     */
    List<User> queryAll();

    /**
     * 按照id批量删除
     */
    boolean deleteList(List<Integer> list);

    /**
     * 根据年龄查询相同年龄的个数
     */
    Long countAge(int age);

    /**
     * 统计数据库中记录数
     */
    long countTotal();

    /**
     * 测试时间格式化
     */
    List<User> getTime();

    List<User> queryByTime(Date startTime, Date endTime);

    /**
     * 查询life所有
     * @return
     */
    List<Life> getLife();

    /**
     * 查询用户关联life 的quality字段
     * @return
     */
    List<User> getUserAndQuality();

    /**
     * 查询用户关联Life
     * @return
     */
    List<User> getUserAndLife();

    /**
     * 查询用户关联Life 将life属性 变为user属性
     * @return
     */
    List<User> getUserAndLife2();

    /**
     * 查询用户关联角色 一对多
     * @return
     */
    List<User> getUserAndRoles();
}