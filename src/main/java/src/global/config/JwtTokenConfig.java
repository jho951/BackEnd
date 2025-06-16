package src.global.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.validation.annotation.Validated;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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
	}

}
