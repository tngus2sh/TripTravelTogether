package com.ssafy.trip.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.trip.exception.UnAuthorizedException;
import com.ssafy.trip.user.model.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

	private static final String HEADER_AUTH = "X-ACCESS-TOKEN";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*'
		 * OPTIONS 메소드는 HTTP 요청 메소드 중 하나
		 * 일반적으로 클라이언트는 GET, POST, PUT, DELETE와 같은 주요한 HTTP 메소드를 사용하여 서버에 요청을 보냄
		 * 그러나 OPTIONS 메소드는 브라우저가 서버에 사전 요청을 보낼 때 사용되는 메소드
		 * 사전 요청은 실제 요청을 보내기 전에 서버에게 허용 가능한 메소드, 헤더 등에 대한 확인을 요청하는 역할
		 * CORS를 사용하는 웹 애플리케이션에서는 브라우저가 OPTIONS 메소드로 사전 요청을 보내고, 서버는 이 요청에 대한 적절한 응답을 제공하여
		 * 클라이언트의 실제 요청이 허용되는지 확인
		 * 이를 통해 클라이언트와 서버 간의 도메인 간 리소스 공유를 안전하게 수행 가능
		 * 따라서, OPTIONS 메소드는 브라우저와 서버 간의 통신에서 CORS 정책을 준수하기 위한 사전 요청에 사용되는 메소드
		 */
    	System.out.println(request.getMethod());
    	if(request.getMethod().equals("OPTIONS"))return true;
    	
		Enumeration<String> headers = request.getHeaders(HEADER_AUTH);
		String value = null;
		while(headers.hasMoreElements()) {
			// 가장 마지막에 발견된 헤더 값
			value = headers.nextElement();
		}

		logger.debug("value: " + value);
		
		String token = request.getHeader(HEADER_AUTH);

		logger.debug("token : " + token);
		if(token != null){
			// 토큰 문자열에서 "Bearer"를 제외한 부분을 추출
			token = token.split(" ")[1];
			// 추출한 토큰을 검증하는 메소드를 호출
			if (jwtService.checkToken(token)) {
				// 토큰이 유효하다면, 로그를 출력하고 요청 처리를 진행
				logger.info("토큰 사용 가능 : {}", token);
				return true;
			} else {
				throw new UnAuthorizedException();
			}
		}else{
			logger.info("토큰 사용 불가능 : {}", token);
			throw new UnAuthorizedException();
		}
	}
}
