package com.study.spring.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.study.spring.security.filter.JWTCheckFilter;
import com.study.spring.security.handler.APILoginFailHandler;
import com.study.spring.security.handler.APILoginSuccessHandler;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomSecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("---------------------security config---------------------------");
		
		http.csrf(config -> config.disable());
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
		http.sessionManagement(sessionConfig ->  sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		 // 1) Swagger(문서)와 로그인 엔드포인트는 공개
		http.authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/v3/api-docs.yaml"
            ).permitAll()
            // 로그인 API 공개 (formLogin을 쓰더라도 login 처리 URL은 열어야 합니다)
            .requestMatchers(HttpMethod.POST, "/api/member/login").permitAll()
            // (선택) 상태 체크 등 공개 엔드포인트
            .requestMatchers("/error", "/actuator/health").permitAll()
            // 그 외는 인증 필요
            .anyRequest().authenticated()
        );
		
		//2) 로그인핸들러
		http.formLogin(config -> {
		      config.loginPage("/api/member/login");
		      config.successHandler(new APILoginSuccessHandler());
		      config.failureHandler(new APILoginFailHandler());
		    });
		
		// 3) 인증/인가 실패 시 HTTP 상태 코드 반환
		http.exceptionHandling(ex -> ex
            .authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .accessDeniedHandler((req, res, e) -> res.sendError(HttpServletResponse.SC_FORBIDDEN))
        );
		
		http.addFilterBefore(new JWTCheckFilter(),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

	    // ✅ React 개발 서버 주소 (정확히)
	    config.setAllowedOrigins(List.of(
	        "http://localhost:5173",
	        "http://127.0.0.1:5173"
	    ));

	    // ✅ 쿠키/인증정보 전송 필요하면 true (refreshToken cookie 쓸 때 필수)
	    config.setAllowCredentials(true);

	    // ✅ 허용 메서드
	    config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));

	    // ✅ 허용 헤더 (Authorization 꼭 포함)
	    config.setAllowedHeaders(List.of("Authorization","Content-Type"));

	    // (선택) 프론트에서 읽어야 하는 헤더가 있으면 노출
	    // config.setExposedHeaders(List.of("Authorization"));

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}
}
