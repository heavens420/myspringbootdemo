package tk.zlx.redistemplate.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class StringValue {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * String 类型操作
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }

    /**
     * String 类型操作
     * @param key
     * @param value
     */
    public void setValueWithBound(String key, String value) {
        stringRedisTemplate.boundValueOps(key).set(value);
    }

    /**
     * 设置String类型 key value 和 key value的过期时间
     * @param key
     * @param value
     */
    public void setValueAndExpire(String key, String value,long expireTimestamp) {
        stringRedisTemplate.opsForValue().set(key,value,expireTimestamp, TimeUnit.SECONDS);
    }

    /**
     * 设置String类型 key value 和 key value的过期时间
     * @param key
     * @param value
     */
    public void setValueAndExpireWithBound(String key, String value, long expireTimestamp) {
        stringRedisTemplate.boundValueOps(key).set(value,expireTimestamp,TimeUnit.SECONDS);
    }

    /**
     * 单独设置过期时间
     * @param key
     * @param expireTimestamp
     */
    public void setExpire(String key,long expireTimestamp) {
        stringRedisTemplate.expire(key, expireTimestamp, TimeUnit.SECONDS);
    }

    /**
     * 单独设置过期时间
     * @param key
     * @param expireTimestamp
     */
    public void setExpireWithBound(String key, long expireTimestamp) {
        stringRedisTemplate.expire(key, expireTimestamp, TimeUnit.SECONDS);
    }

    /**
     * 通过key 获取value
     * @param key
     * @return
     */
    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 通过key 获取value
     * @param key
     * @return
     */
    public String getValueWithBound(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

    /**
     * 增加
     * @param key
     * @param step
     */
    public void incrementValue(String key,long step) {
        stringRedisTemplate.opsForValue().increment(key, step);
    }

    /**
     * 减少
     * @param key
     * @param step
     */
    public void decrementValue(String key, long step) {
        stringRedisTemplate.boundValueOps(key).decrement(step);
    }
}
