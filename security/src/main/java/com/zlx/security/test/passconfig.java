package com.zlx.security.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.PrintWriter;

/**
 * 配置密码有两种方式 直接在yml文件配置 或者用配置类 若同时配置两种方式 代码配置类优先级高
 * <p>
 * 配置类配置密码
 */

@Configuration
public class passconfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder Encoder() {
        //密码不加密 已过时
        return NoOpPasswordEncoder.getInstance();
    }

    //定义角色继承  高级别用户自动拥有低级别用户的权限
    @Bean
    RoleHierarchy  roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return roleHierarchy;
    }


    //重写configure 方法 配置用户名密码角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("heavens")
                .password("111")
                .roles("admin")
                .and()
                .withUser("kk")
                .password("111")
                .roles("user");
    }

    //自定义登录页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated() //任何请求都要认证
                .and()
                .formLogin()
//                .loginPage("login.html")//默认登录接口名称也是 login.html
                .loginProcessingUrl("/tologin") //自定义接口名称
                .usernameParameter("name") //自定义登录参数 默认是 username
                .passwordParameter("passwd") //同上  默认是password
//                .successForwardUrl("/1") //前后端不分离 登录成功自动请求转发到此页面
//                .defaultSuccessUrl("/2") //前后端不分离 登录成功后 重定向到对应页面
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    //向前端 返回json(这里配置返回登录用户的信息 可配置返回其它) 由前端控制页面跳转
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    writer.write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                    writer.flush();
                    writer.close();
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    //登录失败向前端返回 错误信息
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    if (e instanceof BadCredentialsException) {
                        writer.write(new ObjectMapper().writeValueAsString("用户名或密码错误"));
                    }
//                    writer.write(new ObjectMapper().writeValueAsString(e.getMessage()));
                    writer.flush();
                    writer.close();
                })
                .permitAll() //登录相关全部放行
                .and()
                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))//修改登出为post请求
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    //注销登录提示信息
                    httpServletResponse.setContentType("application/json;charset=utf-8");
                    PrintWriter writer = httpServletResponse.getWriter();
                    writer.write(new ObjectMapper().writeValueAsString("注销登录成功"));
                    writer.flush();
                    writer.close();
                })
                .invalidateHttpSession(true)//清空session
                .clearAuthentication(true) //取消认证
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()
//                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
//                    //配置未登录时请求页面 返回提示信息(默认跳转到登录页面)
//                    httpServletResponse.setContentType("application/json;charset=utf-8");
//                    PrintWriter writer = httpServletResponse.getWriter();
//                    writer.write(new ObjectMapper().writeValueAsString("尚未登录，请登录"));
//                    writer.flush();
//                    writer.close();
//                });
        ;
    }

    //放行静态资源
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }
}
