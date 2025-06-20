package src.global.security.jwt.util;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import src.global.security.jwt.dto.JwtRequest;

public class JwtUtil {
	// 인스턴스 방지
	private JwtUtil() {}

	public static String generateToken(JwtRequest.JwtAccessTokenRequest request, SecretKey secretKey) {
		return Jwts.builder()
			.setSubject(request.getSubject())
			.setIssuer(request.getIssuer())
			.setAudience(request.getAudience())
			.setIssuedAt(request.getIssuedAt())
			.setExpiration(request.getExpiration())
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}
}
