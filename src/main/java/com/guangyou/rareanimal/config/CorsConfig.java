package com.guangyou.rareanimal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @Description 跨域配置
 * @author xukai
 * @create 2022-11-14 20:10
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
//                .allowCredentials(true) // 这里设置为true，allowedOrigins("*")方法不能再设置为通配符
                .maxAge(3600);
    }
}
