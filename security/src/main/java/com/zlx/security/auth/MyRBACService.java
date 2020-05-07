package com.zlx.security.auth;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service(value = "rbac") //rbac名称用于动态校验权限
public class MyRBACService {
    //    @Autowired
    @Resource
    private MyRBACSereviceMapper mapper;

    /**
     * 判断用户是否有 url资源的访问权限
     */

    //用于比较 请求路径和用户所拥有的权限url 是否相同
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //获取登录用户信息
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            //获取登录用户的用户名
            String username = ((UserDetails) principal).getUsername();
            List<String> urls = mapper.findUrlByUserName(username);
            System.out.println("succes");

            //数据库中每条记录只能是单个权限 不能是多个权限(url)用逗号隔开 否则该记录的多条权限会被当作是一条权限与请求路径比较，必然匹配失败
            return urls.stream().anyMatch(
                    //匹配用户具有的权限（url）和请求的权限比较 匹配成功返回true 可以访问
                    url -> antPathMatcher.match(url, request.getRequestURI())
            );
        }
        System.out.println("fail");
        //否则 用户无该访问路径的权限 禁止访问
        return false;
    }
}
