package com.zlx.security.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配置密码有两种方式 直接在yml文件配置 或者用配置类 若同时配置两种方式 代码配置类优先级高
 *
 * 配置类配置密码
 */

@Configuration
public class passconfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder Encoder(){
        //密码不加密 已过时
        return NoOpPasswordEncoder.getInstance();
    }

    //重写configure 方法 配置用户名密码角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("heavens")
                .password("111")
                .roles("admin");
    }
}
