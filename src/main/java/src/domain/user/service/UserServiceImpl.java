package src.domain.user.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import src.domain.user.dto.UserRequest;
import src.domain.user.dto.UserResponse;
import src.domain.user.entity.User;
import src.domain.user.entity.UserAuth;
import src.domain.user.repository.UserAuthRepository;
import src.domain.user.repository.UserRepository;
import src.global.constant.code.ErrorCode;
import src.global.exception.BaseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserAuthRepository userAuthRepository;

	@Transactional
	@Override
	public UserResponse.UserCreateResponse create(UserRequest.UserCreateRequest dto) {
		try {
			User createUser = userRepository.save(dto.toCreateEntity());
			UserAuth createUserAuth = userAuthRepository.save(dto.toCreateUserAuthEntity(createUser));
			return UserResponse.UserCreateResponse.from(createUser,createUserAuth);
		} catch (DataIntegrityViolationException e) {
			log.warn("제약 조건 위반: {}", e.getMessage(), e);
			throw new BaseException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
		} catch (PersistenceException e) {
			throw new BaseException(ErrorCode.INVALID_REQUEST_DATA);
		}
	}
}
