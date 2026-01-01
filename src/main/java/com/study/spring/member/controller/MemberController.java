package com.study.spring.member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.study.spring.member.dto.MemberDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MemberController {

	@Operation(
		summary = "내 정보", 
		description = "내 프로필을 조회합니다.",
		security = @SecurityRequirement(name = "Bearer Authentication")
	)
	@GetMapping("/api/user/info")
	 public Map<String, Object> getUserInfo(@AuthenticationPrincipal MemberDto principal,
             Authentication authentication) {
        // Authentication은 JwtAuthFilter에서 설정한 UsernamePasswordAuthenticationToken 객체
            	 if (principal == null) {
            	        return Map.of("authenticated", false, "message", "인증되지 않은 사용자입니다.");
            	    }
            	    return Map.of(
            	        "authenticated", true,
            	        "username", principal.getEmail(),
            	        "authorities", authentication.getAuthorities(),
            	        "message", "JWT 인증 통과 완료!"
            	    );
    }

		@GetMapping("/api/member")
    public String view(){
			return "잘보이네요";
		}
}
