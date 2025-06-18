package src.global.security.exception;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import src.global.response.constant.ErrorCode;
import src.global.response.dto.GlobalResponse;

/**
 * {@code CustomAccessDeniedHandler}는 사용자가 인증되었지만,
 * 요청한 리소스에 대한 권한이 없을 경우 403 Forbidden 응답을 반환하는 클래스입니다.
 * <p>
 * {@link AccessDeniedHandler} 인터페이스를 구현하여 Spring Security 에서 접근 거부 상황을 처리합니다.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

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
	public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * 권한이 없는 사용자가 보호된 리소스에 접근하려고 할 때 호출됩니다.
	 * HTTP 응답으로 403 Forbidden 상태 코드와 함께 표준화된 에러 메시지를 JSON 형식으로 반환합니다.
	 *
	 * @param request  클라이언트의 요청 객체
	 * @param response 서버의 응답 객체
	 * @param accessDeniedException 접근 거부 예외 객체
	 * @throws IOException      입출력 예외 발생 시
	 * @throws ServletException 서블릿 처리 중 예외 발생 시
	 */
	@Override
	public void handle(HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException)
		throws IOException, ServletException {

		// HTTP 상태 코드 설정: 403 Forbidden
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		// 응답 본문의 콘텐츠 타입 설정: JSON + UTF-8 인코딩
		response.setContentType("application/json;charset=UTF-8");

		// 실패 응답 객체 생성
		GlobalResponse<Void> errorResponse = GlobalResponse.fail(ErrorCode.FORBIDDEN);

		// 실패 응답 객체를 JSON 문자열로 직렬화
		String responseBody = objectMapper.writeValueAsString(errorResponse);

		// 직렬화된 JSON 문자열을 HTTP 응답 본문으로 전송
		response.getWriter().write(responseBody);
	}
}
