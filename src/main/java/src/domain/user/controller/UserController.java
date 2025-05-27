package src.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.domain.user.dto.UserRequest;
import src.domain.user.dto.UserResponse;
import src.domain.user.service.UserService;
import src.global.common.dto.GlobalResponse;
import src.global.constant.code.SuccessCode;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public GlobalResponse<UserResponse.UserCreateResponse> cancel(UserRequest.UserCreateRequest dto) {
		UserResponse.UserCreateResponse response = userService.create(dto);
		return GlobalResponse.ok(SuccessCode.SUCCESS, response);
	}
}
