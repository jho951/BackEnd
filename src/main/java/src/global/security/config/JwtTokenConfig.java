package src.global.security.config;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.annotation.Validated;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import src.global.security.jwt.constant.TokenType;

/**
 * application-deb_auth.yml로 부터 주입받습니다.
 */
@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenConfig {
	private Map<String, TokenProperties> tokens = new HashMap<>();

	public TokenProperties get(TokenType type) {
		return tokens.get(type.name().toLowerCase());
	}

	@Getter
	@Setter
	public static class TokenProperties {
		@NotBlank
		private String secretKey;
		@Min(1)
		private long expirationSeconds;
		@NotBlank
		private String issuer;
		@NotBlank
		private String audience;
		@NotBlank
		private String algorithm;
	}
}

