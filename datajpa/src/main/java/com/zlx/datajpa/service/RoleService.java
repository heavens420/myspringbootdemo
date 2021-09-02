package com.zlx.datajpa.service;

import com.zlx.datajpa.entity.Role;
import com.zlx.datajpa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;

    /**
     * 根据roleId查找所有角色及其对应的person
     * @return
     */
    public Role findRolesById(int id){
        return repository.findById(id).get();
    }

    /**
     * 查找所有roles及其对应person
     * @return
     */
    public List<Role> findAllRoles(){
        return repository.findAll();
    }
}
