package com.study.spring.security.handler;



import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.study.spring.member.dto.MemberDto;
import com.study.spring.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {
	
	//Spring Boot 표준 JSON 매퍼 (Gson보다 통합성이 좋음)
	@Autowired
	private  ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	log.info("------------------------------------------");
	log.info(authentication.getPrincipal());
	log.info("------------------------------------------");
	
	
	// 1) 인증 성공한 사용자 정보 꺼내기
    // SecurityContext 에 저장된 Principal 을 우리 도메인 DTO 로 캐스팅
	MemberDto memberDto = (MemberDto) authentication.getPrincipal();
	
	log.info(memberDto);
	
	Map<String, Object> claims = memberDto.getClaims();
	
	// 2) JWT 발급 (단위: 분) — 프로젝트 정책에 맞게 만료 시간 조정
	String accessToken = JWTUtil.generateToken(claims, 10); //10분
	String refreshToken = JWTUtil.generateToken(claims, 60*24); // 24시간
	
	// 3) 응답에 포함할 값 구성 (기존 claims 에 토큰 추가)
	claims.put("accessToken", accessToken);
	claims.put("refreshToken", refreshToken);
	
//	Gson gson = new Gson();
//	
//	String jsonStr = gson.toJson(claims);
//	
//	response.setContentType("application/json;charset=UTF-8");
//	PrintWriter printWriter = response.getWriter();
//	printWriter.println(jsonStr);
//	printWriter.close();
	
	// 응답 설정 및 JSON 출력
	// 명시적으로 HTTP 상태 코드 설정
	// 4) HTTP 응답 작성 
    response.setStatus(HttpStatus.OK.value()); //200ok
    response.setContentType(MediaType.APPLICATION_JSON_VALUE); // JSON 형식으로 알려줌
    response.setContentType("application/json;charset=UTF-8"); // 한글이나 문자대응
    objectMapper.writeValue(response.getOutputStream(), claims); // SON 직렬화 및 출력, 자바 객체 → JSON 응답
	
	
	
		
	}

}
