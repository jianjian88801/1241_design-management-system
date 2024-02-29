package com.xunmaw.design;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * springboot启动类
 *
 * @author xiao.xl 2023/11/1 11:09
 */
@SpringBootApplication
@MapperScan(basePackages = "com.xunmaw.design.dao")
public class DesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesignApplication.class, args);
    }

}
