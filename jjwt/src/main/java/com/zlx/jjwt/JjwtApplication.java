package com.zlx.jjwt;

import com.zlx.jjwt.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JjwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjwtApplication.class, args);
    }

//    @Bean
//    public JwtUtils jwtUtils(){
//        return new JwtUtils();
//    }
}
