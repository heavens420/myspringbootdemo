package com.zlx.jjwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//@Data
//@ConfigurationProperties(prefix = "jwt") 获取不到值
@Component
public class JwtUtils {
    //签名私钥
    String key = "uuuuuuuuuuuu";//太短报错

    //过期时间
    Long ttl = 1000*3600L;

    /**
     * 设置认证token
     *      userId:相当于登录用户id
     *      subject:相当于登录用户名称
     *
     * @param userId
     * @param userName
     * @param map
     * @return
     */
    public  String createJwtToken(Integer userId, String userName, Map<String,Object> map){

        //1 获取失效时间
        long now = System.currentTimeMillis();
        long exp = now + ttl;

        //2 创建jwtBuilter
        JwtBuilder jwtBuilder = Jwts.builder().setId(String.valueOf(userId)).setSubject(userName)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);

        //3 根据map设置claim
//        jwtBuilder.setClaims(map);
        if (map != null){
            for (Map.Entry<String,Object> entry:map.entrySet()){
                jwtBuilder.claim(entry.getKey(),entry.getValue());
            }
        }

        //4 设置失效时间
        jwtBuilder.setExpiration(new Date(exp));

        //5 设置token
        String token = jwtBuilder.compact();
        System.out.println(token);

        return token;
    }


    /**
     * 解析token
     * @param token
     * @return
     */
    public  Claims parseToken(String token){
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("aa","jj");
        String token = new JwtUtils().createJwtToken(111, "uuu", map);
        System.out.println(token);
        Claims claims = new JwtUtils().parseToken(token);
        System.out.println(claims);
    }
}
