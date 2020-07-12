package com.zlx.crud.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.zlx.crud.entity.mongodb.User;
import com.zlx.crud.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoUserImpl implements MongoService {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 新增单个用户
     *
     * @param user
     * @return
     */
    @Override
    public User addUser(User user) {
        return mongoTemplate.insert(user);
    }

    /**
     * 批量增加用户
     *
     * @param list
     * @return
     */
    @Override
    public List<User> batchAddUser(List<User> list) {
        List<User> userList = new ArrayList<>();
        userList.add(new User(2, "川普", 21, "男", "999"));
        userList.add(new User(3, "希拉里", 33, "女", "998"));
        userList.add(new User(4, "拜登", 42, "男", "997"));
        return mongoTemplate.insert(userList);
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Override
    public int deleteUser(Integer userId) {
        Query query = Query.query(Criteria.where("user_id").is(userId));
        Optional.ofNullable(userId).ifPresent(s -> {
            mongoTemplate.remove(query, User.class);
        });
        return userId;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
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
     * @param userId
     * @return
     */
    @Override
    public User findUser(Integer userId) {
        Query query = new Query(Criteria.where("user_id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    /**
     * 查询全部用户
     * @return
     */
    @Override
    public List<User> findAllUser() {
        return mongoTemplate.findAll(User.class);
    }
}
