package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.AuthInterceptor;
import com.example.demo.interceptor.LogInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    AuthInterceptor authInterceptor;
    
    @Autowired
    LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Auth Interceptor chạy trước
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin/**", "/account/**", "/order/**")
                .excludePathPatterns("/auth/**", "/admin/home/index");

        // Log Interceptor chạy sau trên cùng các đường dẫn đó
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/admin/**", "/account/**", "/order/**")
                .excludePathPatterns("/auth/**", "/admin/home/index");
    }
}