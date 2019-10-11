package com.example.user;

import com.example.user.dto.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("globalUserInfo")
    public Map<String, User> globalUserInfo() {
        return new HashMap() {{
            put("张三", new User(1, "张三", new BCryptPasswordEncoder().encode("123456"), "ROLE_ADMIN"));
            put("李四", new User(2, "李四", new BCryptPasswordEncoder().encode("654321"), "ROLE_PASSENGER"));
        }};
    }

}
