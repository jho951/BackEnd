package src.global.common.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import src.global.common.dto.GlobalResponse;
import src.global.constant.code.ErrorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@code CustomAuthenticationEntryPoint}는 사용자가 인증되어 있지 않아
 *  401 Unauthorized 응답을 반환하는 클래스입니다.
 * <p>
 * {@link AccessDeniedHandler} 인터페이스를 구현하여 Spring Security 에서 접근 거부 상황을 처리합니다.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	// 생성자 주입
	private final ObjectMapper objectMapper;

	@Autowired
	public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException)
		throws IOException, ServletException {

		// 응답 본문의 콘텐츠 타입 설정: JSON + UTF-8 인코딩
		response.setContentType("application/json;charset=UTF-8");

		// 실패 응답 객체 생성
		GlobalResponse<Void> errorResponse = GlobalResponse.fail(ErrorCode.UNAUTHORIZED);

		// 실패 응답 객체를 JSON 문자열로 직렬화
		String responseBody = objectMapper.writeValueAsString(errorResponse);

		// 직렬화된 JSON 문자열을 HTTP 응답 본문으로 전송
		response.getWriter().write(responseBody);
	}
}
