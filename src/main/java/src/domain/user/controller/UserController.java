package src.domain.user.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.domain.user.dto.UserRequest;
import src.domain.user.dto.UserResponse;
import src.domain.user.service.UserService;
import src.global.constant.code.SuccessCode;
import src.global.common.response.GlobalResponse;

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
	//
	// @PutMapping("/update")
	// public GlobalResponse<UserResponse.UserUpdateResponse> update(UserRequest.UserUpdateRequest dto) {
	// 	UserResponse.UserUpdateResponse response = userService.update(dto);
	// 	return GlobalResponse.ok(SuccessCode.SUCCESS,response);
	// }
}
