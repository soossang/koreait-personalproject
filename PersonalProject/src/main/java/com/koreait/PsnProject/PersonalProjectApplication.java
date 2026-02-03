package com.koreait.PsnProject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// ✅ 1. import 문 추가
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// ✅ 2. exclude 속성 추가
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@MapperScan("com.koreait.PsnProject.dao")
public class PersonalProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonalProjectApplication.class, args);
    }
}