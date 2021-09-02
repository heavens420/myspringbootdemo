package tk.zlx.redistemplate.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class CommonOperation {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 删除某个key
     * @param key
     */
    public void deleteKey(String key){
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     * @param keys
     */
    public void deleteKeys(String ...keys){
//        redisTemplate.delete(keys);  此方法报类型转换异常
        redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 指定key的失效时间
     * @param key
     * @param time
     */
    public void expireKey(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取key的过期时间
     * @param key
     * @return
     */
    public long getExpireTime(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean existKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
