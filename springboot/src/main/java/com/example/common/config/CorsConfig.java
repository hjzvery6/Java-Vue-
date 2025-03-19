package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// SpringBoot配置类，用于设置跨域资源共享，主要是允许前端应用从不同的域名、端口或者协议访问后端接口
@Configuration // 标记该类为配置类，Spring容器会扫描并加载类中的Bean定义
public class CorsConfig {
    // 将方法返回的对象注册为Spring容器中的一个Bean
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 允许所有来源的跨域请求
        corsConfiguration.addAllowedHeader("*"); // 允许所有请求头
        corsConfiguration.addAllowedMethod("*"); // 允许所有HTTP方法
        source.registerCorsConfiguration("/**", corsConfiguration); // 将CORS配置应用到所有URL
        return new CorsFilter(source);
    }
}