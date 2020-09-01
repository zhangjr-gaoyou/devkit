package com.zhang.devkit.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhang.devkit.demo.mapper")
public class MybatisPlusDemoApplication {

        public static void main(String[] args) {
            SpringApplication.run(MybatisPlusDemoApplication.class, args);
        }

}
