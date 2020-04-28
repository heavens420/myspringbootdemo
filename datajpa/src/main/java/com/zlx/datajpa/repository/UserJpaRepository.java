package com.zlx.datajpa.repository;

import com.zlx.datajpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//JpaRepository 继承自 PagingAndSortingRepository 接口 所以 拥有前面接口的所有方法
//而且 JpaRepository 的findAll（）方法 返回的是 List 类型不再是 Itorable 类型

//JpaSpecificationExecutor 复杂分页和查询

//1 实现动态查询
//2 Jpa方法命名查询: 方法名称 xxUser(s)Byxx 格式固定否则报错
//3 注解查询
public interface UserJpaRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    /**
     * Jpa方法命名查询
     *
     */

    //根据名称精确查询
    User findUserByName(String name);

    //根据名称模糊查询
    List<User> findUsersByNameIsLike(String name);

    @Query("from t_user where name like %?1% and id > ?2")
    List<User> FindNameLike(String name,int id);

    //使用原生sql
    @Query(value = "select * from t_user where addr like %?1% and id < ?2" ,nativeQuery = true)
    List<User> FindAddrLike(String addr,int id);

    //使用@Param注解   冒号后面的内容与 注解中的内容必须一致
    @Query(value = "select * from t_user where addr = :addr",nativeQuery = true)
    List<User> FindAddrIs(@Param("addr") String addr);

    //必须加事务才行
    @Modifying
    @Transactional
    @Query("update t_user set addr = ?1 ,name = ?2 where id = ?3")
    void UpdateUserById(String addr,String name,int id);
}
