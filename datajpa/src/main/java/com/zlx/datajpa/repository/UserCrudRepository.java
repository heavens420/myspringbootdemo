package com.zlx.datajpa.repository;

import com.zlx.datajpa.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

//基础crud类

public interface UserCrudRepository extends CrudRepository<User, Integer>{

}
