package src.global.security.jwt.util;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import src.global.base.constant.ErrorCode;
import src.global.base.exception.GlobalException;
import src.global.security.jwt.dto.JwtRequest;
import src.global.security.jwt.constant.TokenType;

public class JwtUtil {
	private JwtUtil() {}

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
}
