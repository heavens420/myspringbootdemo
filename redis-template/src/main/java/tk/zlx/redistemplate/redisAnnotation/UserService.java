package tk.zlx.redistemplate.redisAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.zlx.redistemplate.entities.User;
import tk.zlx.redistemplate.repository.UserRepository;


@Service
@CacheConfig(cacheNames = "public-cache-name")
public class UserService {

    @Autowired
    private UserRepository repository;

    @Cacheable(value = "user",key = "#id")
    public User findById(Integer id){
        System.out.println("查询了数据库");
        return repository.findById(id).get();
    }

    @CachePut(value = "user",key = "'id'")
    @Transactional(rollbackFor = Exception.class)
    public int updateUserById(int id){
        return repository.updatebyId(id);
    }

//    allEntries保证清除所有 value = “user” 的缓存
    @CacheEvict(value = "user",allEntries = true)
    public int deleteUserById(int id) {
        return repository.deleteById(id);
    }

    @CachePut(value = "user",key = "#user.id")
    public User addUser(User user){
        return repository.save(user);
    }

    @Cacheable(value = "str",key = "'str'")
    public String getValue(){
        System.out.println("str query");
        return "String Value111";
    }

    /**
     * 条件注解 condition
     * @param id
     * @return
     */
    @Cacheable(value = "zs", key = "'zs'+#id",condition = "#id eq 1")
    public User getZhangsan(int id){
        return repository.findById(id).get();
    }
}
