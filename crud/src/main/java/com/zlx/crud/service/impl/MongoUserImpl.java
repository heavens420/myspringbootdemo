package com.zlx.crud.service.impl;

import com.zlx.crud.config.MD5;
import com.zlx.crud.entity.mongodb.User;
import com.zlx.crud.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MongoUserImpl implements MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增单个用户
     *
     * @param user 用户对象
     * @return     插入的对象
     */
    @Override
    public User addUser(User user) {
        user.setPasswd(MD5.md5(user.getUserId()+"",user.getPasswd()));
        return mongoTemplate.insert(user);
    }

    /**
     * 批量增加用户
     *
     * @param list      用户集合
     * @return          插入的集合
     */
    @Override
    public List<User> batchAddUser(List<User> list) {
        list.forEach(user -> user.setPasswd(MD5.md5(user.getUserId()+"",user.getPasswd())));
        return (List<User>) mongoTemplate.insertAll(list);
    }

    /**
     * 删除用户
     *
     * @param userId    用户id
     * @return          影响行数
     */
    @Override
    public int deleteUser(Integer userId) {
        Query query = Query.query(Criteria.where("user_id").is(userId));
        Optional.ofNullable(userId).ifPresent(s -> mongoTemplate.remove(query, User.class));
        return userId != null?userId:-1;
    }

    /**
     * 更新用户
     *
     * @param user  用户实体对象
     * @return      用户实体对象
     */
    @Override
    public User updateUser(User user) {
        Query query = new Query(Criteria.where("user_id").is(user.getUserId()));
        Update update = new Update();
        update.set("age", user.getAge());
        update.set("name", user.getName());
        update.set("passwd", user.getPasswd());
        update.set("sex",user.getSex());
        mongoTemplate.updateFirst(query, update, User.class);
        return user;
    }

    /**
     * 查询用户
     *
     * @param userId 用户id
     * @return       用户实体对象
     */
    @Override
    public User findUser(Integer userId) {
        Query query = new Query(Criteria.where("user_id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    /**
     * 查询全部用户
     * @return 全部用户
     */
    @Override
    public List<User> findAllUser() {
        return mongoTemplate.findAll(User.class);
    }

    /**
     * 模糊查询 name
     * @param name  按name查询
     * @return      符合的User集合
     */
    @Override
    public List<User> getUserByRegex(String name) {
        Query query = new Query(Criteria.where("name").regex(name));
        return mongoTemplate.find(query, User.class);
    }

    /**
     * 根据年龄范围和性别查询
     * @param age   年龄
     * @param sex   性别
     * @return      用户实体对象集合
     */
    @Override
    public List<User> getUsers(int age, String sex) {
        Query query = new Query(Criteria.where("age").gt(age).andOperator(Criteria.where("sex").is(sex)));
        return mongoTemplate.find(query,User.class);
    }


}
