package tk.zlx.aop.aopannotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import tk.zlx.aop.aopannotation.exception.AOPException;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate template;

    public boolean setExpireTime(String key, String value, long seconds) {
        boolean result = false;
        try {
            final ValueOperations<String, String> ops = template.opsForValue();
            ops.set(key, value);
            template.expire(key, 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
