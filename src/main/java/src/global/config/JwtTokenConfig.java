package src.global.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Configuration
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenConfig {

	private TokenProperties accessToken;
	private TokenProperties refreshToken;

	public static class TokenProperties {
		@NotBlank
		private String secret;
		@Min(1)
		private long expirationSeconds;

		public long getExpirationMillis() {
			return expirationSeconds * 1000L;
		}
	}
}
