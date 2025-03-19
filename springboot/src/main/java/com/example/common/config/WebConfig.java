package com.example.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.Resource;

// SpringBoot项目的配置类，用于定义和注册自定义拦截器
@Configuration // 标记该类为Spring的配置类
public class WebConfig implements  WebMvcConfigurer {
    // 使用@Resource注解将JwtInterceptor注入到该类中
    @Resource
    private JwtInterceptor jwtInterceptor;

    // 加自定义拦截器JwtInterceptor，设置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor) // 添加自定义拦截器
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/login") // 排除登录、注册、文件上传请求
                .excludePathPatterns("/register")
                .excludePathPatterns("/files/**")
        ;
    }
}