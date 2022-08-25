package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.Grade;
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
     *  简单复杂查询方式一
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
                    // 这里传数组必须是Integer类型 不能是int类型 不管User.id是 int还是Integer类型
                    predicate.getExpressions().add(root.get("id").in(new Integer[]{1,2,4}));
                    predicate.getExpressions().add(criteriaBuilder.greaterThan(root.get("id"), 2));
                }

                return predicate;
            }
        }, pageable);
        return users;
    }

    /**
     * 简单复杂查询方式二
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
                predicates.toArray(predicates1);
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

                // select role_id from t_person where roleId = 1
                Subquery<Person> subquery = criteriaQuery.subquery(Person.class);
                Root<Person> person = subquery.from(Person.class);
                subquery.select(person.get("role")).where(criteriaBuilder.equal(person.get("id"),1));

                // select id from t_role where roleName = "s" and roleId in (select  role_id from t_person where role_id = 1)
                Subquery<Role> subquery1 = criteriaQuery.subquery(Role.class);
                Root<Role> role = subquery1.from(Role.class);
                subquery1.select(role.get("id")).where(role.get("id").in(subquery));

                // select * from user where id in (上面的select语句)
                return criteriaQuery.where((root.get("id")).in(subquery1),criteriaBuilder.equal(root.get("name"),name)).getRestriction();
            }
        },pageable);
    }

    /**
     * 复杂子查询 方式二 封装查询条件
     * @param pageable
     * @param name
     * @return
     */
    public Page<User> findUserBySubQuery2(Pageable pageable,String name){
        return repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 最外层查询条件
                Predicate predicate = criteriaBuilder.conjunction();
                // 子查询1条件
                Predicate predicate1 = criteriaBuilder.conjunction();
                // 子查询2条件
                Predicate predicate2 = criteriaBuilder.conjunction();

                Subquery<Person> subquery = criteriaQuery.subquery(Person.class);
                Root<Person> person = subquery.from(Person.class);
                predicate1.getExpressions().add(criteriaBuilder.equal(person.get("id"), 1));
                // 同第一种写法 只是把子查询条件封装了一下
                subquery.select(person.get("role")).where(predicate1);

                Subquery<Role> subquery1 = criteriaQuery.subquery(Role.class);
                Root<Role> role = subquery1.from(Role.class);
                // 封装查询条件
                predicate2.getExpressions().add(criteriaBuilder.equal(role.get("id"), 222));
                subquery1.select(role.get("id")).where(role.get("id").in(subquery),predicate2);

                predicate.getExpressions().add(criteriaBuilder.equal(root.get("name"), name));
                // 也可以将子查询 也进行封装
//                predicate.getExpressions().add(root.get("id").in(subquery1));
                // 用 where 和 and 都可以 写法稍有不同
                return criteriaBuilder.and((root.get("id")).in(subquery1),predicate);
                // 将所有条件进行封装 直接传入
//                return criteriaBuilder.and(predicate);
            }
        },pageable);
    }

    /**
     * 复杂子查询 方式三 封装查询条件
     * @param pageable
     * @param name
     * @return
     */
    public Page<User> findUserBySubQuery3(Pageable pageable,String name){
        return repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 最外层查询条件
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 子查询1条件
                List<Predicate> predicateList2 = new ArrayList<Predicate>();
                // 子查询2条件
                List<Predicate> predicateList3 = new ArrayList<Predicate>();

                Subquery<Person> subquery = criteriaQuery.subquery(Person.class);
                Root<Person> person = subquery.from(Person.class);
                // 同第一种写法 只是把子查询条件封装了一下
                predicateList2.add(criteriaBuilder.equal(person.get("id"), 1));
                subquery.select(person.get("role")).where(predicateList2.toArray(new Predicate[predicateList2.size()]));

                Subquery<Role> subquery1 = criteriaQuery.subquery(Role.class);
                Root<Role> role = subquery1.from(Role.class);
                // 封装查询条件
                predicateList3.add(criteriaBuilder.equal(role.get("id"), 222));
                // 封装了所有条件
                predicateList3.add(role.get("id").in(subquery));
                subquery1.select(role.get("id")).where(predicateList3.toArray(new Predicate[predicateList3.size()]));

                predicateList.add(criteriaBuilder.equal(root.get("name"), name));
                predicateList.add(root.get("id").in(subquery1));
                // 用 where 和 and 都可以 写法稍有不同
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        },pageable);
    }

    /**
     * 两表关联查询 注意要配合关联对象中的注解使用
     * @param pageable
     * @param userId
     * @return
     */
    public Page<User> findUserJoinRole(Pageable pageable,int userId){
        return repository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                //  Page<User> 所以主表是User 从User里面找 role属性
                Join<User, Role> join = root.join("role", JoinType.LEFT);
                // 连接条件 on 后面的条件
//                join.on(criteriaBuilder.equal(join.get("roleName"),"zhangsan"));
                // join条件构造的是 role对象也就是User里面的role属性对应的对象的属性条件 如下：where role.id = userId and user.id = 123
//                predicate.getExpressions().add(criteriaBuilder.equal(join.get("id"), userId));
//                predicate.getExpressions().add(criteriaBuilder.equal(root.get("id"), "123"));
                return predicate;
            }
        },pageable);
    }

    /**
     * 关联对象查询 注意要配合关联对象中的注解使用
     * @param pageable
     * @return
     */
    public Page<User> findByUserJoinGrade(Pageable pageable) {
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                Join<User, Grade> join = root.join("gradeList", JoinType.INNER);
                // 一对多或多对多数据可能重复 用group by 过滤重复数据
                criteriaQuery.groupBy(root.get("id"));
                return predicate;
            }
        };
        return repository.findAll(specification,pageable);
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
