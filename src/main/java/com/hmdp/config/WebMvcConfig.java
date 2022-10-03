package com.hmdp.config;

import com.hmdp.interceptor.LoginInterceptor;
import com.hmdp.interceptor.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author taoyes3
 * @date 2022/10/2 20:35
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private RefreshTokenInterceptor refreshTokenInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 刷新token拦截器
        registry.addInterceptor(refreshTokenInterceptor).addPathPatterns("/**").order(0);
        // 登录拦截器
        registry.addInterceptor(loginInterceptor).excludePathPatterns(
                "/shop/**",
                "/voucher/**",
                "/shop-type/**",
                "/upload/**",
                "/user/code",
                "/user/login",
                "/blog/hot"
        ).order(1);
    }
}
