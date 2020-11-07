package com.easy.boke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.easy.boke.dao")
public class BokeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BokeServiceApplication.class, args);
    }

}
