package src.domain.user.controller;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.domain.user.dto.UserRequest;
import src.domain.user.dto.UserResponse;
import src.domain.user.service.UserService;
import src.global.swagger.constant.SwaggerTag;
import src.global.response.dto.GlobalResponse;
import src.global.response.constant.SuccessCode;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Tag(name = SwaggerTag.USER, description = "This is a user controller")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public GlobalResponse<UserResponse.UserCreateResponse> cancel(UserRequest.UserCreateRequest dto) {
		UserResponse.UserCreateResponse response = userService.create(dto);
		return GlobalResponse.ok(SuccessCode.SUCCESS, response);
	}

	@PostMapping("/login")
	public GlobalResponse<UserResponse.UserAuthResponse>login(UserRequest.UserAuthRequest dto) {
		UserResponse.UserAuthResponse response = userService.auth(dto);
		return GlobalResponse.ok(SuccessCode.SUCCESS, response);
	}
}
