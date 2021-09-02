package com.zlx.datajpa.repository;

import com.zlx.datajpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role> {

}
