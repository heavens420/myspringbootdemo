package com.zlx.crud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Zhao LongLong
 * @Date 2020/9/5
 * @Version 1.0
 * @Desc 自定义注解用于 权限校验(在拦截器解析注解，使注解生效)
 */

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {

}
