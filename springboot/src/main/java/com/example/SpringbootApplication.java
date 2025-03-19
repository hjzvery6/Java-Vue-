// 启动类，启动类在启动时会扫描注解和配置，创建和初始化相应的组件
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.dao") // 指定Mapper接口的扫描路径，使这些接口能被MyBatis自动识别
public class SpringbootApplication {
    // 启动程序
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
