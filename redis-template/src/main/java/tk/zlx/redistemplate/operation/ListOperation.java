package tk.zlx.redistemplate.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ListOperation {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 从左往右 向list中添加元素
     * @param key
     * @param values
     */
    public void addValueFromLeft(String key, String... values) {
//        redisTemplate.boundListOps(key).leftPush(Arrays.asList(values));
        redisTemplate.opsForList().leftPush(key, Arrays.asList(values));
    }

    /**
     * 从右往左 向list中添加元素
     * @param key
     * @param values
     */
    public void addValueFromRight(String key, String... values) {
//        redisTemplate.boundListOps(key).rightPush(Arrays.asList(values));
        redisTemplate.opsForList().rightPush(key, Arrays.asList(values));
    }

    /**
     * 单独设置过期时间
     * @param key
     * @param expireTimestamp
     */
    public void setExpire(String key, long expireTimestamp) {
//        redisTemplate.boundListOps(key).expire(expireTimestamp, TimeUnit.SECONDS);
        redisTemplate.expire(key, expireTimestamp, TimeUnit.SECONDS);
    }

    /**
     * 获取list指定区间的元素  0代表第一个  -1代表最后一个元素 -2倒数第二个 以此类推
     * @param key
     * @param startIndex
     * @param endIndex
     * @return
     */
    public List<Object> getAllListValues(String key,long startIndex,long endIndex) {
//        return redisTemplate.boundListOps(key).range(startIndex, endIndex);
        return redisTemplate.opsForList().range(key, startIndex, endIndex);
    }

    /**
     * 从list左边出栈一个元素
     * @param key
     */
    public void leftPop(String key) {
//        redisTemplate.boundListOps(key).leftPop();
        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 从list右边出栈一个元素
     * @param key
     */
    public void rightPop(String key) {
//        redisTemplate.boundListOps(key).rightPop()();
        redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 根据索引查找元素
     * @param key
     * @param index
     * @return
     */
    public Object getValue(String key,long index) {
//        return redisTemplate.boundListOps(key).index(index);
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取list长度
     * @param key
     * @return
     */
    public long getSize(String key) {
//        return redisTemplate.boundListOps(key).size();
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 根据元素索引修改list元素的值
     * @param key
     * @param index
     * @param newValue
     */
    public void modifyByIndex(String key, long index,String newValue) {
        redisTemplate.boundListOps(key).set(index,newValue);
        redisTemplate.opsForList().set(key, index, newValue);
    }

    /**
     * 移除count个 值为value的元素
     * @param key
     * @param count
     * @param value
     */
    public void removeValues(String key, long count,String value) {
//        redisTemplate.boundListOps(key).remove(count, value);
        redisTemplate.opsForList().remove(key,count,value);
    }
}
