package tk.zlx.redistemplate.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class SetOperation {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加 set类型数据
     * @param key
     * @param values
     */
    public void setSet(String key, String... values) {
//        redisTemplate.boundSetOps(key).add(Arrays.asList(values));
        redisTemplate.opsForSet().add(key, Arrays.asList(values));
    }

    /**
     * 单独设置 过期时间
     * @param key
     * @param expireTimeStamp
     */
    public void setExpire(String key, long expireTimeStamp) {
        redisTemplate.boundSetOps(key).expire(expireTimeStamp, TimeUnit.SECONDS);
        redisTemplate.expire(key, expireTimeStamp, TimeUnit.SECONDS);
    }

    /**
     * 获取set中的所有值
     * @param key
     * @return
     */
    public Set<Object> getAllSet(String key) {
//        return  redisTemplate.boundSetOps(key).members();
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断set中是否存在某个value
     * @param key
     * @param value
     * @return
     */
    public boolean existValue(String key,String value) {
//        return redisTemplate.boundSetOps(key).isMember(value);
        return redisTemplate.opsForSet().isMember(key,value);
    }

    /**
     * 获取set的长度
     * @param key
     * @return
     */
    public long getSetLength(String key) {
//        return redisTemplate.boundSetOps(key).size();
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除set中指定的value
     * @param key
     * @param values
     */
    public void removeValue(String key, String ...values) {
//        redisTemplate.boundSetOps(key).remove(Arrays.asList(values));
        redisTemplate.opsForSet().remove(key, Arrays.asList(values));
    }
}
