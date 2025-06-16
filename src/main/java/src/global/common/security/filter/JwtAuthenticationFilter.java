package src.global.common.security.filter;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import src.global.common.security.auth.utils.JwtTokenProvider;

/**
 * OncePerRequestFilter // 요청마다 단 한 번만 실행되는 필터.
 */
@Component // Spring Bean 등록
@RequiredArgsConstructor // final 필드인 jwtTokenProvider를 자동으로 생성자 주입
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request,
		@NotNull HttpServletResponse response,
		@NotNull FilterChain filterChain)
		throws ServletException, IOException {

		// 토큰 추출
		String token = jwtTokenProvider.resolveToken(request);
		// 토큰 유효성 검사
		if (token != null && jwtTokenProvider.validateToken(token)) {
			// 인증 객체 생성
			Authentication auth = jwtTokenProvider.getAuthentication(token);
			// SecurityContext에 등록
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		// 인증된 사용자로 요청 처리
		filterChain.doFilter(request, response);
	}
}