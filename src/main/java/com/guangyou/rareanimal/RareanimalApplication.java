package com.guangyou.rareanimal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.guangyou.rareanimal.mapper")
public class RareanimalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RareanimalApplication.class, args);
    }

}
