package src.global.security.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import src.global.security.user.CustomUser;
import src.global.security.jwt.AccessTokenService;
import src.global.security.jwt.RefreshTokenService;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AccessTokenService accessTokenService;
	private final RefreshTokenService refreshTokenService;
	private final AuthenticationManager authenticationManager;

	public TokenResponse login(LoginRequest request) {
		// 인증 시도
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.email(), request.password())
		);

		CustomUser user = (CustomUser) authentication.getPrincipal();

		// 토큰 발급
		String accessToken = accessTokenService.generate(user);
		String refreshToken = refreshTokenService.generate(user);

		return new TokenResponse(accessToken, refreshToken);
	}
}
