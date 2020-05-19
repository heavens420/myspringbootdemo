package com.zlx.crud.service;

import com.zlx.crud.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface PermissionService {
    Set<String> queryPermissioinByUserId(Integer userId);
}
