package com.weiyang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weiyang.db.mappers")
public class FirstFlashSaleApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstFlashSaleApplication.class);
    }
}
