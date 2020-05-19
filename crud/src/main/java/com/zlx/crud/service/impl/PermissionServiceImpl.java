package com.zlx.crud.service.impl;

import com.zlx.crud.dao.PermissionDao;
import com.zlx.crud.entity.Permission;
import com.zlx.crud.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public Set<String> queryPermissioinByUserId(Integer userId) {
        return permissionDao.queryPermissioinByUserId(userId);
    }
}
