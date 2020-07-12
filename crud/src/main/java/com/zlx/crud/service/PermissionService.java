package com.zlx.crud.service;

import java.util.Set;

public interface PermissionService {
    Set<String> queryPermissioinByUserId(Integer userId);
}
