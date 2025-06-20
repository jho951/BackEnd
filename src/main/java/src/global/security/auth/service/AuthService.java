package src.global.security.auth.service;

import src.global.security.auth.dto.AuthRequest;
import src.global.security.auth.dto.AuthResponse;

public interface AuthService {
	AuthResponse.AuthLoginResponse Login(AuthRequest.AuthLoginRequest dto);
}
