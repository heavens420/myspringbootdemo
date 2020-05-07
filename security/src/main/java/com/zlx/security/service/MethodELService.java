package com.zlx.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * 方法级别的安全注解测试案例
 *
 *
 * @PreAuthorize 鉴权之前
 * @PostAuthorize 鉴权之后
 * @PreFilter  过滤之前
 * @PostFilter  过滤之后
 */
@Service
public class MethodELService {

    //拥有admin的角色可以访问该方法
    @PreAuthorize("hasRole('admin')")
    public String preAu(){
        return "PreAuthorize";
    }
}
