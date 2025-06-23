package src.global.security.jwt.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import src.global.security.jwt.config.JwtTokenConfig;

public class JwtRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JwtTokenRequest {
		private String subject;
		private String issuer;
		private String audience;
		private Date issuedAt;
		private Date expiration;

		public static JwtTokenRequest of(String userId, JwtTokenConfig.TokenProperties props, Date issuedAt, Date expiry) {
			return JwtTokenRequest.builder()
				.subject(userId)
				.issuer(props.getIssuer())
				.audience(props.getAudience())
				.issuedAt(issuedAt)
				.expiration(expiry)
				.build();
		}
	}
}
