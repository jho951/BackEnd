package src.global.common.security.jwt;


import java.util.Date;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import src.global.common.security.user.CustomUserDetailsService;
import src.global.config.JwtTokenConfig;

/**
 *  JWT 발급 및 해석, 인증 객체 생성까지 전반적인 JWT 처리를 담당하는 핵심 유틸 클래스
 */
@Component // Spring Bean 등록
@RequiredArgsConstructor // final 필드 자동 생성자 주입
public class JwtTokenProvider {

	// 사용자 ID로 사용자 정보
	private final CustomUserDetailsService customUserDetailsService;
	// JWT 설정 클래스 (application-dev-auth.yml
	private final JwtTokenConfig jwtTokenConfig;

	public String createToken(String userId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtTokenConfig.getExpirationMs());

		return Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.signWith(SignatureAlgorithm.HS256, jwtTokenConfig.getSecretKey())
			.compact();
	}


	public String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		// 토큰 유효성 검사 구현 (만료 여부, 시그니처 등)
		return true;
	}

	public Authentication getAuthentication(String token) {
		String userId = getUserIdFromToken(token);
		var userDetails = customUserDetailsService.loadUserByUsername(userId);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	private String getUserIdFromToken(String token) {
		// 실제 claim에서 userId 꺼내기
		return "userId-from-token";
	}
}
