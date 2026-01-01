package com.study.spring.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;




@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler {

	
	
	
	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		
		log.info("------------------------------------------");
		log.info("Login fail....." + exception);
		log.info("------------------------------------------");

		Gson gson = new Gson();
	    
	    String jsonStr = gson.toJson(Map.of("error", "ERROR_LOGIN"));

	    response.setContentType("application/json");
	    PrintWriter printWriter = response.getWriter();
	    printWriter.println(jsonStr);
	    printWriter.close();   
		
//		// 1)로그 기록
//        log.warn("❌ 로그인 실패: {}", exception.getMessage());
//
//        // 2) 실패 응답 데이터 구성
//        Map<String, Object> errorBody = Map.of(
//                "error", "ERROR_LOGIN",
//                "message", "아이디 또는 비밀번호가 올바르지 않습니다."
//        );
//
//        // 3️) HTTP 응답 설정 및 전송
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());               // 401 Unauthorized
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);         // application/json
//        objectMapper.writeValue(response.getOutputStream(), errorBody);    // JSON 변환 후 출력
		
	}

	
	
}
