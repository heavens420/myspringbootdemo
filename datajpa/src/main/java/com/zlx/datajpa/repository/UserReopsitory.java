package com.zlx.datajpa.repository;

import com.zlx.datajpa.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReopsitory extends JpaSpecificationExecutor<User>, JpaRepository<User,Long> {
}
