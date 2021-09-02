package com.zlx.crud.interceptor;

import com.zlx.crud.annotation.Permission;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Zhao LongLong
 * @Date 2020/9/5
 * @Version 1.0
 * @Desc 自定义 springMVC拦截器
 */

@Component
public class MyInterceptor /*extends HandlerInterceptorAdapter*/ implements HandlerInterceptor {

    /**
     * 进入到控制器前要执行的方法 返回true表示 可以继续执行控制器里面的方法 返回false拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 登录成功 生成token
        // 1 此处可以校验jwt 存在token返回true 否则返回 false，抛出未登录异常信息
        // 2 以及对权限进行校验，
        // 3 或者 对自定义注解 进行解析

        //解析 自定义注解
        // 未添加该注解的 返回 true不受影响不拦截 正常进入执行器 正常执行 返回false 全拦截
//        if (!(handler instanceof HandlerMethod)){
//            return true;
//        }

        // 解析注解
        Method method = ((HandlerMethod) handler).getMethod();
        Permission permission = method.getAnnotation(Permission.class);

        // 添加该注解的 拦截
        if (permission != null){
            // 此处可 返回是否有相应权限，实现权限校验
//            return false;
            throw new Exception("@Permission注解使你 权限不足");
        }
        return true;
    }

    /**
     * 执行控制器方法之后 要执行的方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 响应结束后要执行的方法
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    public static void main(String[] args) {
        Map map = new HashMap(3);
        map.put("type","userName");
        map.put("name","张三");
        Map map1 = new HashMap(3);
        map1.put("type","userName");
        map1.put("name","李四");
        Map map2 = new HashMap(3);
        map2.put("type","userName");
        map2.put("name","王五");

        Map map4 = new HashMap(3);
        map4.put("type","productName");
        map4.put("name","烟");
        Map map5 = new HashMap(3);
        map5.put("type","productName");
        map5.put("name","洒");
        Map map6 = new HashMap(3);
        map6.put("type","productName");
        map6.put("name","茶");
        List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
        fileList.add(map);
        fileList.add(map1);
        fileList.add(map2);
        fileList.add(map4);
        fileList.add(map5);
        fileList.add(map6);

        System.out.println(fileList);

        Map<String, List<Map<String, String>>> fileListMap = fileList.stream().collect(
                Collectors.groupingBy(e -> e.get("type")));
        System.out.println(fileListMap);}
}
