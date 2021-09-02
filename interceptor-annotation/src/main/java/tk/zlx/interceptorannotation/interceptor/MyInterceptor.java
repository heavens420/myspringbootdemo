package tk.zlx.interceptorannotation.interceptor;

import org.springframework.stereotype.Component;
import tk.zlx.interceptorannotation.annontation.MyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tk.zlx.interceptorannotation.exception.MyException;
import tk.zlx.interceptorannotation.service.TokenService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return  true;
        }

        Method method =((HandlerMethod) handler).getMethod();
        MyLog log = method.getAnnotation(MyLog.class);

        if (log != null){
            try {
                return service.checkToken(request);
            }catch (MyException e){
                throw e;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
