package src.global.security.auth.service;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import src.global.base.exception.GlobalException;
import src.global.base.constant.ErrorCode;
import src.global.security.auth.entity.Auth;
import src.global.security.auth.dto.AuthRequest;
import src.global.security.auth.dto.AuthResponse;
import src.global.security.jwt.service.AccessTokenService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AccessTokenService accessTokenService;
	private final AuthenticationManager authenticationManager;

	@Transactional
	@Override
	public AuthResponse.AuthLoginResponse Login(AuthRequest.AuthLoginRequest dto) {
		try {
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
			);

			Auth auth = (Auth) authentication.getPrincipal();

			String token = accessTokenService.createToken(userAuth.getId().toString());

			return AuthResponse.AuthLoginResponse.from(auth, token);
		} catch (AuthenticationException e) {
			throw new GlobalException(ErrorCode.NOT_FOUND_USER);
		} catch (DataIntegrityViolationException e) {
			throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
		} catch (PersistenceException e) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST_DATA);
		}
	}
}
