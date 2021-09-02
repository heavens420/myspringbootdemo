package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.Person;
import com.zlx.datajpa.entity.Role;
import com.zlx.datajpa.entity.User;
import com.zlx.datajpa.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     *  复杂查询方式一
     * @param page
     * @param size
     * @param user
     * @return
     */
    public Page<User> findAllByPagingAndSorting(int page, int size, User user) {
        //分页不排序
//        Pageable pageable = PageRequest.of(page, size);

        //分页排序
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "id");

        Page<User> users = repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //获取条件对象
                Predicate predicate = criteriaBuilder.conjunction();

                //判断是否为空
                if (user != null) {
                    //name
                    if (!StringUtils.isEmpty(user.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name").as(String.class), "%" + user.getName() + "%"));
                    }
                    if (!StringUtils.isEmpty(user.getPhone())) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("phone"), user.getPhone()));
                    }
                    if (!StringUtils.isEmpty(user.getAddr())){
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("addr"), "%" + user.getAddr() + "%"));
                    }
                    predicate.getExpressions().add(criteriaBuilder.greaterThan(root.get("id"), 2));
                }

                return predicate;
            }
        }, pageable);
        return users;
    }

    /**
     * 复杂查询方式二
     * @param pageable
     * @param name
     * @return
     */
    public Page<User> findUserByNameLike(Pageable pageable, String name) {
        return repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (!StringUtils.isEmpty(name)) {
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+name+"%"));
//                    predicates.add((Predicate) criteriaBuilder.desc(root.get("name")));
                }
                // 不包含 3，4
                predicates.add(criteriaBuilder.not(root.get("id").in(Arrays.asList(3, 4))));

                Predicate[] predicates1 = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(predicates1));
            }
        }, pageable);
    }

    /**
     * 复杂查询之子查询
     * @param pageable
     * @param name
     * @param address
     * @return
     */
    public Page<User> findUserBySubQuery(Pageable pageable,String name,String address){
        return repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate predicate = criteriaBuilder.conjunction();

                // select role_id from t_person where roleId = 1
                Subquery subquery = criteriaQuery.subquery(String.class);
                Root person = criteriaQuery.from(Person.class);
                subquery.select(person.get("id")).where(criteriaBuilder.equal(person.get("id"),1));

                // select id from t_role where roleName = "s" and roleId in (select  role_id from t_person where role_id = 1)
                Subquery subquery1 = criteriaQuery.subquery(String.class);
                Root role = criteriaQuery.from(Role.class);
                subquery1.select(role.get("id")).where(criteriaBuilder.equal(role.get("roleName"), "s"),role.get("id").in(subquery));

                // select * from user where id in (上面的select语句)
                return criteriaBuilder.and(root.get("id").in(subquery1),criteriaBuilder.equal(root.get("name"),name));

            }
        },pageable);
    }

    public Page<User> findUserBySubQuery2(Pageable pageable,String name){
        return repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Subquery subquery = criteriaQuery.subquery(String.class);
                Root person = criteriaQuery.from(Person.class);
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(person.get("id"), 1));

                return null;

            }
        },pageable);
    }

    /**
     * 两表关联查询
     * @param pageable
     * @param id
     * @return
     */
    public Page<User> findUserJoinRole(Pageable pageable,int id){
        return repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                Join<User, Role> join = root.join("id", JoinType.LEFT);
                predicate.getExpressions().add(criteriaBuilder.equal(join.get("id"), id));
                return predicate;
            }
        },pageable);
    }

//    public User exampleMatcher(User user){
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnorePaths("里斯")
//                .withIncludeNullValues();
//    }

    //方法名称命名查询
    public User findUserByName(String name) {
        return repository.findUserByName(name);
    }

    public List<User> findUsersByNameIsLike(String name) {
        return repository.findUsersByNameIsLike("%" + name + "%");
    }

    //注解查询
    public List<User> findNameLike(String name, int id) {
        return repository.findNameLike(name, id);
    }

    public List<User> findAddrLike(String addr, int id) {
        return repository.findAddrLike(addr, id);
    }

    public List<User> findAddrIS(String addr) {
        return repository.findAddrIs(addr);
    }

    public void updateUserById(String addr, String name, int id) {
        repository.updateUserById(addr, name, id);
    }


}
