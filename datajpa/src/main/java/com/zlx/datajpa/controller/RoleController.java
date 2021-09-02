package com.zlx.datajpa.controller;

import com.zlx.datajpa.entity.Role;
import com.zlx.datajpa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/{id}")
    public Map<String, Role> getRoles(@PathVariable("id") int id){
        HashMap map = new HashMap(8);
        Role role = roleService.findRolesById(id);
        map.put("role", role);
        return map;
    }

    @GetMapping("/roles")
    public Map<String, Object> getAllRoles(){
        HashMap map = new HashMap(8);
        Set set = new HashSet(roleService.findAllRoles());
        map.put("roles", set);
        return map;
    }

}

