package src.domain.user.service;

import src.domain.user.dto.UserRequest;
import src.domain.user.dto.UserResponse;

public interface UserService {
	UserResponse.UserCreateResponse create(UserRequest.UserCreateRequest dto);
	UserResponse.UserAuthResponse auth(UserRequest.UserAuthRequest dto);
}
