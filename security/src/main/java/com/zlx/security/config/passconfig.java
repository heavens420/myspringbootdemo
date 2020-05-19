package com.zlx.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlx.security.auth.MyExpiredSessionStrategy;
import com.zlx.security.auth.MyInvalidSessionStrategy;
import com.zlx.security.auth.MySuccessHandler;
import com.zlx.security.auth.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.io.PrintWriter;

/**
 * 配置密码有两种方式 直接在yml文件配置 或者用配置类 若同时配置两种方式 代码配置类优先级高
 * <p>
 * 配置类配置密码
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启全局方法 级别安全注解
public class passconfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MySuccessHandler mySuccessHandler;

    @Autowired
    private MyInvalidSessionStrategy sessionStrategy;

    @Autowired
    private MyExpiredSessionStrategy expiredSessionStrategy;

    @Autowired
    private MyUserDetailsService service;

    @Autowired
    private DataSource dataSource;

    @Bean
    PasswordEncoder encoder() {
        //密码不加密 已过时
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    //定义角色继承  高级别用户自动拥有低级别用户的权限
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return roleHierarchy;
    }

    //将session存入 数据库
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    //重写configure 方法 配置用户名密码角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("heavens")
//                .password("111")
//                .roles("admin")
//                .and()
//                .withUser("kk")
//                .password("111")
//                .roles("user");

        //从数据库中加载用户密码和权限 并对密码加密
        auth.userDetailsService(service).passwordEncoder(encoder());
    }

    //自定义登录页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .rememberMe()
                .rememberMeParameter("rememberme") //设置rememberme的名称默认为 remember-me
                .rememberMeCookieName("rememberMeCookie") //设置rememberme的cookie名称默认为
                .tokenValiditySeconds(60 * 60) //rememberme cookie 过期时间
                .tokenRepository(persistentTokenRepository()) //将token信息存入数据库
                .and()
                .formLogin()
//                .loginPage("login.html")//默认登录接口名称也是 login.html
                .loginProcessingUrl("/tologin") //自定义接口名称
                .usernameParameter("name") //自定义登录参数 默认是 username
                .passwordParameter("passwd") //同上  默认是password
//                .successForwardUrl("/1") //前后端不分离 登录成功自动请求转发到此页面
//                .defaultSuccessUrl("/2") //前后端不分离 登录成功后 重定向到对应页面
//                .successHandler((httpServletRequest, httpServletResponse, authentication) -> { //也可以自定义实现类实现此功能 下亦同
//                    //向前端 返回json(这里配置返回登录用户的信息 可配置返回其它) 由前端控制页面跳转
//                    httpServletResponse.setContentType("application/json;charset=utf-8");
//                    PrintWriter writer = httpServletResponse.getWriter();
//                    writer.write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
//                    writer.flush();
//                    writer.close();
//                })
                .successHandler(mySuccessHandler) //在配置类中配置 并注入spring 效果同上
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
                    //重定向到登录页
                    httpServletResponse.sendRedirect("/login");
                    writer.flush();
                    writer.close();
                })
//                .logoutSuccessUrl("/login")
//                .logoutUrl("/login")
                .invalidateHttpSession(true)//使session失效
                .clearAuthentication(true) //取消认证
                .deleteCookies("JSESSIONID") //删除cookie
                .permitAll()

                // .anyRequest().authenticated() //任何请求都要认证
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
//                })
                //开启session
                .and()
                .authorizeRequests()
                //访问xxxx接口 或url 需要 yyyy 权限
//                .antMatchers("/syslog").hasAuthority("/sys_log")
//                .antMatchers("/sysuser").hasAuthority("/sys_user")
//                .antMatchers("/admin/1").hasAuthority("/admin/1")
                //访问xxxx 接口或url 需要 yyyy角色
//                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/user/**").hasRole("user")
//                .antMatchers("/**").hasAnyAuthority("ROLE_admin")  也可以这样设置访问权限
                //动态加载数据库中的权限并校验 rbac为校验接口的名称
                .anyRequest().access("@rbac.hasPermission(request,authentication)")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                //session过期 跳到自定义的登录页
//                .invalidSessionUrl("/tologin");
                .sessionFixation()
                //session 每次登录重新生成新的session并将旧的session的会话复制到新的session 此为默认配置
                .migrateSession()
                //每次重新登录生成新的session 不会创建新的会话 而是使用servlet提供的会话固定保护
//                .changeSessionId()
                //每次登录创建新的session
//                .newSession()
                //不会创建sessio一直使用旧的session
//                .none();
                .invalidSessionStrategy(sessionStrategy)
                //最大session数量 即最多同时登录数量
                .maximumSessions(1)
                //设置为true时 登录数量超限无法登录 false时 超出最大登录数量限制则踢掉已登录用户
                .maxSessionsPreventsLogin(false)
                //session数量超限后系统进行的提示
                .expiredSessionStrategy(new MyExpiredSessionStrategy());
        ;
    }

    //放行静态资源
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }


}
