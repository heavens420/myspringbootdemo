package tk.zlx.redistemplate.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.zlx.redistemplate.entities.User;
import tk.zlx.redistemplate.redisAnnotation.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/get/{id}")
    public Map<String, User> getUser(@PathVariable(value = "id") Integer id){
        Map<String,User> map = new HashMap();
        User user = service.findById(id);
        map.put("user",user);
        return map;
    }

    @RequestMapping("/put/{id}")
    public int updateUserById(@PathVariable(value = "id") int id) {
        return service.updateUserById(id);
    }

    @RequestMapping("/del/{id}")
    public int deleteUserById(@PathVariable(value = "id") int id) {
        return service.deleteUserById(id);
    }

    @PostMapping("/add/user")
    public User addUser(@RequestBody User user){
        return service.addUser(user);
    }

    @RequestMapping("/str")
    public String getValue(){
        return service.getValue();
    }

    @RequestMapping("/zs/{id}")
    public User findZs(@PathVariable(value = "id") int id){
        return service.getZhangsan(id);
    }
}
