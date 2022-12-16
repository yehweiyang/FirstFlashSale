package com.weiyang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.weiyang.db.mappers")
public class FirstFlashSaleApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstFlashSaleApplication.class);
    }
}
