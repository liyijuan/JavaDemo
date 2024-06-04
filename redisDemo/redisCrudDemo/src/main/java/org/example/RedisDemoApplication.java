package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@MapperScan("org.example.mapper")
@SpringBootApplication
public class RedisDemoApplication {
    public static void main( String[] args ) {
        SpringApplication.run(RedisDemoApplication.class);
    }
}
