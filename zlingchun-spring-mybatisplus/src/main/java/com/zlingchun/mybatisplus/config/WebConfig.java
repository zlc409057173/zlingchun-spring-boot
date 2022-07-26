package com.zlingchun.mybatisplus.config;

import com.zlingchun.mybatisplus.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error","/favicon.ico","/swagger-resources/**","/v2/**","/swagger-ui/**","/css/**","/fonts/**","/images/**","/js/**","/webjars/**","/user/login");
    }
}
