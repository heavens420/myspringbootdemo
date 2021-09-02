package tk.zlx.interceptorannotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 设置过期时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setExpiration(String key,Object value,long expireTime){
        boolean result = false;
        try {
            final ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * key 是否存在
     * @param key
     * @return
     */
    public boolean existsKey(String key){
        return redisTemplate.hasKey(key);
    }

    public boolean removeKey(String key){
        if (existsKey(key)){
            return redisTemplate.delete(key);
        }
        return false;
    }
}
