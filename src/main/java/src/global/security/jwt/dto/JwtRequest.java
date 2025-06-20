package src.global.security.jwt.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

public class JwtRequest {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JwtAccessTokenRequest {
		private String subject;
		private String issuer;
		private String audience;
		private Date issuedAt;
		private Date expiration;
	}
}
