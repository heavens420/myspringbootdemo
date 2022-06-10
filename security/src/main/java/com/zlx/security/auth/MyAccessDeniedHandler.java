package com.zlx.security.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义403状态返回内容
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        // 403情况指定返回内容
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);

        httpServletResponse.setContentType("application/json; charset=UTF-8");

        final PrintWriter writer = httpServletResponse.getWriter();
        writer.write("status:403");
        writer.flush();
        writer.close();
    }
}
