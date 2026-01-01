package com.study.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // SecurityScheme 정의: Bearer JWT 인증 방식 설정
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT 토큰만 입력하세요 (Bearer는 자동으로 추가됩니다). 예: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...");

        return new OpenAPI()
                .info(new Info()
                        .title("Security API Documentation")
                        .version("1.0.0")
                        .description("Spring Security JWT 인증 기반 API 문서"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", bearerAuth));
                // 전역 SecurityRequirement 제거 - 각 API에 개별적으로 적용
    }
}
