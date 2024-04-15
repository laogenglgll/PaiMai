package com.er.paiyipai.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class PaiYiPaiConfiguration implements WebMvcConfigurer {
    @Autowired
    private AccessInterceptor accessInterceptor;
    //配置拦截器
    //addPathPatterns:拦截器拦截哪些地址     /** 所有地址
    //excludePathPatterns:不拦截哪些地址
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(accessInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register","/user/jihuo")
                .excludePathPatterns("/admin/login","/admin/register");
    }
}
