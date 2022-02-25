package tk.jing.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tk.jing.mp.entity.User;
import tk.jing.mp.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Resource
    private UserMapper mapper;

    @Test
    public void queryUserList(){
        final List selectList = mapper.selectList(new QueryWrapper());
        selectList.forEach(System.out::println);
    }

    @Test
    public void insertUser() {
        User user = new User();
        user.setUserName("mp2222");
        user.setAddress("beijign2222222222222 ");
        user.setAge(120);
        mapper.insert(user);
    }

    @Test
    public void updateUser() {
        User user = new User();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", 2);
        user.setId(4L);
        user.setUserName("update");
        user.setAge(200);
        mapper.update(user,queryWrapper);
    }

    @Test
    public void updateUserById() {
        User user = new User();
        user.setId(4L);
        user.setUserName("update");
        user.setAge(200);
        mapper.updateById(user);
    }

    public void saveUser() {
//        mapper.
    }

    @Test
    public void queryByMap(){
        Map map = new HashMap(8);
        map.put("name", "zhangsan");
        final List list = mapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    @Test
    public void queryUserMethods() {
        QueryWrapper<User> wrapper = new QueryWrapper();
//        QueryWrapper wrapper = Wrappers.query();

        // select * from user where name like '%upd% and age < 100'
        wrapper.like("name", "upd").lt("age", 100);

        // select * from user where name like '%zhang%' and age between(10,20) and address is not null
        wrapper.like("name", "zhang").between("age", 10, 20).isNotNull("address");

        // select * from user where name like '%ni' and age = 20 order by id desc
        wrapper.likeLeft("name", "ni").eq("age", 20).orderByDesc("id");

        // select * from user where date_format(create_time,'%Y-%d-%m) = "2022-02-25" and id in (select id from xxx)
        wrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2022-02-25").inSql("id", "select id from xxx");

        // select * from user where name like 'ni%' and (age < 20 or address is not null)
        wrapper.likeRight("name", "ni").and(qw -> qw.lt("age", 20).or().isNotNull("address"));

        // select * from user where (age < 20 or address is not null) and name like '%ni%'
        wrapper.nested(qw -> qw.lt("age", 20).or().isNotNull("address")).like("name", "ni");

        // select * from user where id in (1,2,3)
        wrapper.in("id", new ArrayList<>(Arrays.asList(1, 2, 3)));

        // select * from user where id in (1,2,3) limit 1
        wrapper.in("id", new ArrayList<>(Arrays.asList(1, 2, 3))).last("limit 1");


        final List<User> users = mapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void queryUserTwo() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.
    }



}
