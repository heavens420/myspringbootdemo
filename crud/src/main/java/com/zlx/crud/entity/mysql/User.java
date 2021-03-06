package com.zlx.crud.entity.mysql;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-01-08 09:25:53
 */
public class User implements Serializable {
    private static final long serialVersionUID = 518515160285583803L;
    
    private Integer id;
    
    private Integer age;
    
    private String name;
    
    private String phone;
    
    private String addr;

    private String passwd;

    private Integer lifeId;

    private List<Role> roles;

//    private String quality;

    private Life life;

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public String getPasswd() {
        return passwd;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getLifeId() {
        return lifeId;
    }

//    public String getQuality() {
//        return quality;
//    }
//
//    public void setQuality(String quality) {
//        this.quality = quality;
//    }

    public void setLifeId(Integer lifeId) {
        this.lifeId = lifeId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //将前台传来的日期格式化
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //将后台传给前台的日期格式化
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User() {
    }

    public User(Integer age, String name, String phone, String addr) {
        this.age = age;
        this.name = name;
        this.phone = phone;
        this.addr = addr;
    }

    public User(Integer id, Integer age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", addr='" + addr + '\'' +
                ", time=" + time +
                '}';
    }
}