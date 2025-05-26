package src.global.common.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;

import src.global.common.security.user.CustomUser;
import src.global.config.JwtTokenConfig;
import src.global.config.JwtTokenConfig.TokenProperties;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

	private final JwtTokenConfig jwtTokenConfig;

	private SecretKey secretKey;
	private long expirationMillis;

	@PostConstruct
	public void init() {
		TokenProperties accessProps = jwtTokenConfig.getAccessToken();
		this.secretKey = Keys.hmacShaKeyFor(accessProps.getSecret().getBytes());
		this.expirationMillis = accessProps.getExpirationSeconds() * 1000;
	}

}
