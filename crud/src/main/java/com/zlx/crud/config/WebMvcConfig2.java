package com.zlx.crud.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author Zhao LongLong
 * @Date 2020/9/6
 * @Version 1.0
 * @Desc  jwt鉴权 拦截器
 */
public class WebMvcConfig2 extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }
}
