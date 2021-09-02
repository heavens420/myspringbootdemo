package tk.zlx.redistemplate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

/**
 * 解决redis乱码问题
 *      1、针对String类型乱码，使用StringRedisTemplate 代替 RedisTemplate
 *      2、使用此配置类，解决所有乱码问题
 */
@Configuration
public class RedisConfig {

    /**
     * redis序列化方式选择：
     * 1.默认的JdkSerializationRedisSerializer序列化方式，其编码是ISO-8859-1,会出现乱码问题
     * 2.StringRedisSerializer序列化方式，其编码是UTF-8,可以解决乱码问题；序列化字符串无双引号
     * 3.Jackson2JsonRedisSerializer序列化方式，其编码是UTF-8,可以解决乱码问题，但是字符串会有一个双引号
     */

    @Autowired
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        initRedisTemplate();
    }

    private void initRedisTemplate() {
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        //设置Key序列化要使用的模板，默认是JdkSerializationRedisSerializer
        redisTemplate.setKeySerializer(stringSerializer);
        //设置次莫版要使用的哈希key(或field)序列化程序，默认是JdkSerializationRedisSerializer
        redisTemplate.setHashKeySerializer(stringSerializer);
        //设置value序列化要使用的模板，默认是JdkSerializationRedisSerializer
        redisTemplate.setValueSerializer(stringSerializer);
        //设置此模板要使用的哈希值序列化程序，默认是JdkSerializationRedisSerializer
        redisTemplate.setHashValueSerializer(stringSerializer);
    }
}
