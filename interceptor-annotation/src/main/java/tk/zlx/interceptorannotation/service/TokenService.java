package tk.zlx.interceptorannotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.zlx.interceptorannotation.exception.MyException;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class TokenService {

    @Autowired
    RedisService service;

    /**
     * 模拟创建token
     * @return
     */
    public String createToken(){
        String uuid = UUID.randomUUID().toString();
        service.setExpiration(uuid, uuid, 60);
        return uuid;
    }

    public boolean checkToken(HttpServletRequest request) throws MyException {
        //0 此处的token需要通过postman手动设置 token:value
        //1 当postman没有设置token 则 抛异常 token不存在
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            token = request.getParameter("token");
            if (StringUtils.isEmpty(token)){
                throw new MyException("token 不存在");
            }
        }

        //2 postman设置token之后，redis中 token存在取反为false 不执行此方法
        //4 第二次执行时 redis中的token在3已删除 token取反为true 执行此方法 抛异常
        //5 若此时redis中存在其它token 则不执行此方法
        if (!service.existsKey(token)){
            throw new MyException("重复的操作");
        }

        //3 第一次执行 删除redis中的token
        //6 redis中存在其它token，不存在当前token时  根据当前postman中的token去删除redis中的token找不到，删除失败，标志位此时位true
        boolean removeToken = service.removeKey(token);

        //7 执行了6 标志位位true 则执行此方法 抛出异常
        if (!removeToken){
            throw new MyException("重复的操作");
        }
        return true;
    }
}
