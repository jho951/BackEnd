package src.global.security.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.annotation.Validated;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * application-deb_auth.yml로 부터 주입받습니다.
 */
@Configuration
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenConfig {

	private TokenProperties accessToken;
	private TokenProperties refreshToken;

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
	}
}
