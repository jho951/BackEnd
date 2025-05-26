package src.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtTokenConfig {

	private TokenProperties accessToken;
	private TokenProperties refreshToken;

	@Getter
	@Setter
	public static class TokenProperties {
		private String secret;
		private long expirationSeconds;
	}
}
