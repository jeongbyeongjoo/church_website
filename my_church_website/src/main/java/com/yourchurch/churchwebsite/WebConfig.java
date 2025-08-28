package com.yourchurch.churchwebsite;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 이 클래스가 스프링의 설정 파일임을 나타냅니다.
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 관련 설정을 추가하는 메서드입니다.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // "/api/"로 시작하는 모든 경로에 대해
                .allowedOrigins("http://localhost:3000") // "http://localhost:3000" (React 앱) 으로부터의 요청을 허용합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드를 지정합니다.
                .allowedHeaders("*") // 모든 헤더를 허용합니다.
                .allowCredentials(true); // 쿠키/인증 정보를 함께 보낼 수 있도록 허용합니다.
    }
}