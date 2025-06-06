package src.domain.user.service;

import src.domain.user.dto.UserRequest;
import src.domain.user.dto.UserResponse;

public interface UserService {
	UserResponse.UserCreateResponse create(UserRequest.UserCreateRequest dto);
	// UserResponse.UserUpdateResponse update(UserRequest.UserUpdateRequest dto);
}
