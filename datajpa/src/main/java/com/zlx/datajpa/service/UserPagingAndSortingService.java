package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.repository.UserPagingAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserPagingAndSortingService {

    @Autowired
    private UserPagingAndSortingRepository repository;

    //排序
    public Iterable<User> FindAllBySort(){
        //2.2.1以前写法
//        Sort sort = new Sort(Sort.Direction.DESC,"id");
        //2.2.1及之后 Sort 构造器私有 不能再new
        //定义排序规则
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return repository.findAll(sort);
    }

    //分页
    //page 当前页码
    //size 每页数量

    public Iterable<User> FindAllByPageAndSort(int page,int size){

        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        //设置按id降序排列
//        return repository.findAll(PageRequest.of(page, size, Sort.Direction.DESC,"id"));
        return repository.findAll(pageRequest);
    }
}
