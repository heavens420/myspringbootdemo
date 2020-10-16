package com.zlx.crud.config;

import com.zlx.crud.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 配置跨域请求
 * 不需要在接口上加 @OrigigonCross注解
 */

@Configuration
public class WebMvcConfig  /*extends WebMvcConfigurationSupport or */ implements WebMvcConfigurer{

    @Autowired
    MyInterceptor myInterceptor;

    /**
     * 设置跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") //或配置 具体域名 https://jing.tk
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(30*1000); //put请求在发请求前会发送探测请求 设置探测请求过期时间
    }

    /**
     * 添加自定义注解拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有
        registry.addInterceptor(myInterceptor).addPathPatterns("/**");
    }

}
