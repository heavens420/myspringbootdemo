package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCrudRepositoryService {

    @Autowired
    private UserCrudRepository repository;

    public User add(User user) {
        return repository.save(user);
    }

    public User FindUserById(int id) {
        return repository.findById(id).get();
    }

    public Iterable<User> FindUserAll() {
        return repository.findAll();
    }

    public void DeleteById(int id) {
        repository.deleteById(id);
    }

    public long GetUserCount(){
        return repository.count();
    }
}
