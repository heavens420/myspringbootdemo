package com.zlx.datajpa.repository;

import com.zlx.datajpa.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

//排序 分页
// PagingAndSortingRepository 继承自 CrudRepository
public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<User,Integer> {
}
