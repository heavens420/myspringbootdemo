package com.zlx.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserDetailsMapper mapper;

    @Autowired
    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 通过自带的jdbc创建用户 每次都会执行所以要判断是否存在
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        if (!jdbcUserDetailsManager.userExists("admin")) {
            jdbcUserDetailsManager.createUser(User.withUsername("admin").password("admin").roles("admin").build());
        }

        //加载用户基础信息
        MyUserDetails user = mapper.findByUserName(s);

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        //加在用户角色信息
        List<String> roles = mapper.findRoleByUserName(s);

        //加载用户权限信息
        List<String> authority = mapper.findAuthorityByRoleCode(roles);


        //角色格式为 ROLE_xxx 处理如下
        roles = roles.stream().map(r -> "ROLE_" + r).collect(Collectors.toList());

        //给角色添加权限
        authority.addAll(roles);

        user.setAuthorities(
                //将逗号分隔的权限转换为 集合list
                AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authority))
        );
        return user;
    }
}
