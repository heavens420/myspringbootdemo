package tk.zlx.redistemplate.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class HashOperation {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加单值hash
     *
     * @param key
     * @param id
     * @param value
     */
    public void setHash(String key, String id, String value) {
//        redisTemplate.boundHashOps(key).put(id,value);
        redisTemplate.opsForHash().put(key, id, value);
    }

    /**
     * 添加多值hash
     *
     * @param key
     * @param map
     */
    public void setHashMap(String key, Map<String, Object> map) {
//        redisTemplate.boundHashOps(key).putAll(map);
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 单独设置过期时间
     * @param key
     * @param expireTimeStamp
     */
    public void setExpire(String key, long expireTimeStamp) {
//        redisTemplate.boundHashOps(key).expire(expireTimeStamp, TimeUnit.SECONDS);
        redisTemplate.expire(key, expireTimeStamp, TimeUnit.SECONDS);
    }

    /**
     * 根据hash的key 获取其小key
     * @param key
     * @return
     */
    public Set<Object> getHashKeys(String key) {
//        return redisTemplate.boundHashOps(key).keys();
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 根据hash的key 获取其所有value
     * @param key
     * @return
     */
    public List getHashValues(String key) {
        return redisTemplate.boundHashOps(key).values();
    }

    /**
     * 根据hash的key 获取其所有的key和value
     * @param key
     * @return
     */
    public Map<String, Object> getHashMap(String key) {
//        return redisTemplate.boundHashOps(key).entries();
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除 hash的 某一个键值对
     * @param key
     * @param childKey
     */
    public void deleteKey(String key, String childKey) {
//        redisTemplate.boundHashOps(key).delete(key1);
        redisTemplate.opsForHash().delete(key, childKey);
    }

    /**
     * 判断 hash中 是否存在某个子key
     * @param key
     * @param childKey
     * @return
     */
    public boolean isExistKey(String key,String childKey){
//        return redisTemplate.boundHashOps(key).hasKey(childKey);
        return redisTemplate.opsForHash().hasKey(key, childKey);
    }
}
