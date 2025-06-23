package src.global.security.jwt.util;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;

import src.global.base.constant.ErrorCode;
import src.global.security.jwt.dto.JwtRequest;
import jakarta.servlet.http.HttpServletRequest;
import src.global.base.exception.GlobalException;
import src.global.security.jwt.constant.TokenType;

public class JwtUtil {
	private JwtUtil() {
		throw new UnsupportedOperationException("유틸 클래스는 인스턴스화할 수 없습니다.");
	}

	public static String generateToken(JwtRequest.JwtTokenRequest request, SecretKey secretKey, SignatureAlgorithm algorithm) {
		return Jwts.builder()
			.setSubject(request.getSubject())
			.setIssuer(request.getIssuer())
			.setAudience(request.getAudience())
			.setIssuedAt(request.getIssuedAt())
			.setExpiration(request.getExpiration())
			.signWith(secretKey, algorithm)
			.compact();
	}

	public static void validateToken(String userId, TokenType type) {
		if (type == null) {
			throw new GlobalException(ErrorCode.INVALID_TOKEN_TYPE);
		}
		if (userId == null) {
			throw new GlobalException(ErrorCode.INVALID_USER_ID);
		}
	}

	public static SignatureAlgorithm getSignatureAlgorithm(String name) {
		try {
			return SignatureAlgorithm.forName(name);
		} catch (Exception e) {
			throw new GlobalException(ErrorCode.INVALID_JWT_ALGORITHM);
		}
	}

	public static String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if (bearer != null && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}


	// ✅ 서명 검증 + Claims 추출
	public static Claims parseToken(String token, SecretKey key) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();  // ← Payload (Claims) 반환
	}

	public static boolean validateToken(String token,  SecretKey secretKey) {
		try {
			JwtUtil.parseToken(token, secretKey);
			return true;
		} catch (ExpiredJwtException e) {
			System.out.println("JWT 만료: " + e.getMessage());
		} catch (JwtException e) {
			System.out.println("JWT 유효하지 않음: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("JWT 파싱 오류: " + e.getMessage());
		}
		return false;
	}

	// ✅ 토큰에서 사용자 ID 추출
	public static String extractUserId(String token, SecretKey secretKey) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}
}
