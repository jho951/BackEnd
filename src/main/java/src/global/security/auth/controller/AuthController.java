package src.global.security.auth.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.global.swagger.constant.SwaggerTag;
import io.swagger.v3.oas.annotations.tags.Tag;
import src.global.base.dto.GlobalResponse;
import src.global.base.constant.SuccessCode;
import src.global.security.auth.dto.AuthRequest;
import src.global.security.auth.dto.AuthResponse;
import src.global.security.auth.service.AuthService;

@RestController
@RequestMapping("api/v1/Auth")
@RequiredArgsConstructor
@Tag(name = SwaggerTag.AUTH, description = "This is a Auth controller")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public GlobalResponse<AuthResponse.AuthLoginResponse> login(AuthRequest.AuthLoginRequest dto) {
		AuthResponse.AuthLoginResponse response = authService.Login(dto);
		return GlobalResponse.ok(SuccessCode.SUCCESS, response);
	}
}
