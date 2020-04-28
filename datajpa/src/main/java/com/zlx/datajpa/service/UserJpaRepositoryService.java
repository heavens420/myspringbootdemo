package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 1 实现动态查询
 * 2 方法命名查询
 * 3 注解查询
 */

@Service
public class UserJpaRepositoryService {

    @Autowired
    private UserJpaRepository repository;

    public Page<User> FindAllByPagingAndSorting(int page,int size,User user){

        //分页不排序
//        Pageable pageable = PageRequest.of(page, size);

        //分页排序
        Pageable pageable = PageRequest.of(page -1 , size, Sort.Direction.DESC, "id");

        Page<User> users = repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //获取条件对象
                Predicate predicate = criteriaBuilder.conjunction();

                //判断是否为空
                if (user != null) {
                    //name
                    if (user.getName() != null && !user.getName().equals("")) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + user.getName() + "%"));
                    }
                    if (user.getPhone() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("phone"), user.getPhone()));
                    }
                }
                return predicate;
            }
        },pageable);
        return users;
    }


    //方法名称命名查询
    public User findUserByName(String name){
        return repository.findUserByName(name);
    }

    public List<User> findUsersByNameIsLike(String name){
        return repository.findUsersByNameIsLike("%"+name+"%");
    }

    //注解查询
    public List<User> FindNameLike(String name,int id){
        return repository.FindNameLike(name,id);
    }

    public List<User> FindAddrLike(String addr,int id){
        return repository.FindAddrLike(addr,id);
    }

    public List<User> FindAddrIS(String addr){
        return repository.FindAddrIs(addr);
    }

    public void UpdateUserById(String addr,String name,int id){
        repository.UpdateUserById(addr,name,id);
    }
}
