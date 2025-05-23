package src.global.common.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import src.global.common.dto.GlobalResponse;
import src.global.constant.code.ErrorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@code CustomAuthenticationEntryPoint}는 사용자가 인증되지 않은 상태에서 보호된 리소스에 접근할 경우,
 * HTTP 401 Unauthorized 상태 코드와 함께 표준화된 JSON 응답을 반환하는 클래스입니다.
 * <p>
 * {@link AuthenticationEntryPoint} 인터페이스를 구현하여 Spring Security의 인증 실패 상황을 처리합니다.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * JSON 직렬화를 위한 {@link ObjectMapper} 인스턴스.
	 */
	private final ObjectMapper objectMapper;

	/**
	 * {@link ObjectMapper}를 주입받는 생성자입니다.
	 *
	 * @param objectMapper JSON 직렬화를 처리할 ObjectMapper
	 */
	@Autowired
	public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * 인증되지 않은 사용자가 보호된 리소스에 접근했을 때 호출됩니다.
	 * HTTP 응답으로 401 Unauthorized 상태 코드와 함께 에러 메시지를 JSON 형식으로 반환합니다.
	 *
	 * @param request       클라이언트의 요청 객체
	 * @param response      서버의 응답 객체
	 * @param authException 인증 실패 예외 객체
	 * @throws IOException      입출력 예외가 발생한 경우
	 * @throws ServletException 서블릿 처리 중 예외가 발생한 경우
	 */
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
