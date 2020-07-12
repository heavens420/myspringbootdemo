package com.zlx.crud.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * mongoDB User 集合实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    @Field("user_id")
    private Integer userId;

    @Field("name")
    private String name;

    @Field("age")
    private Integer age;

    @Field("sex")
    private String sex;

    @Field("passwd")
    private String passwd;

    public User(Integer userId, String name, Integer age, String sex, String passwd) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.passwd = passwd;
    }
}
