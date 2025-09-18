package com.aiproject.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagesConfig implements WebMvcConfigurer {

    private String UPLOAD = "c:/upload/images"; // 외부 저장

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 외부 업로드 이미지 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///" + UPLOAD + "/");
        
        // 내부 정적 리소스 매핑
        registry.addResourceHandler("/static-images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
