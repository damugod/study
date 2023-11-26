package com.jonhon.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Value("${jonhon.sysId:NONE}")
    private String sysId;
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceFilter(sysId));
        registrationBean.addUrlPatterns("/*"); // 指定过滤的 URL 路径
        return registrationBean;
    }

    @Bean
    public TraceAsyncConfigurer traceAsyncConfigurer(){
        return new TraceAsyncConfigurer();
    }
}
