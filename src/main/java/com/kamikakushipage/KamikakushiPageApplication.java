package com.kamikakushipage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kamikakushipage.mapper")
public class KamikakushiPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(KamikakushiPageApplication.class, args);
    }

}
