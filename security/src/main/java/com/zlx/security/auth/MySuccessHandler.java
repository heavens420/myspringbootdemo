package com.zlx.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当请求成功时返回json
 * 也可在config里面配置
 *
 */
@Component
public class MySuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {//或实现 Successhandler 接口

    //使用springboot自带的 将字符串转换为json的类
    private static ObjectMapper mapper  = new ObjectMapper();

    private String  url;

    public MySuccessHandler() {
    }

    public MySuccessHandler(String url) {
        this.url = url;
    }

    // 自定义登录成功重定向地址，可以是任意网站地址
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        super.onAuthenticationSuccess(request, response, chain, authentication);
        if (url != null && !"".equals(url)) {
            response.sendRedirect(url);
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        //返回json数据  也可以自己封装返回数据实体类
        response.getWriter().write(mapper.writeValueAsString(authentication.getPrincipal()));
    }
}
