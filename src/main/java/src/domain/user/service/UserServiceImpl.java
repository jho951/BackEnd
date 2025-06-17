package src.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.PersistenceException;

import src.domain.user.entity.User;
import src.domain.user.entity.UserAuth;
import src.domain.user.dto.UserRequest;
import src.domain.user.dto.UserResponse;
import src.domain.user.entity.UserSocial;
import src.global.constant.code.ErrorCode;
import src.global.exception.GlobalException;
import src.domain.user.repository.UserRepository;
import src.domain.user.repository.UserAuthRepository;
import src.domain.user.repository.UserSocialRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final UserAuthRepository userAuthRepository;
	private final UserSocialRepository userSocialRepository;

	/**
	 * @param dto 회원가입 유저 정보
	 * @return user 테이블에 유저 정보 저장
	 */
	private User saveUser(UserRequest.UserCreateRequest dto) {
		User user = dto.toUserEntity();
		return userRepository.save(user);
	}

	/**
	 * @param dto,user 회원가입 유저 정보, user 엔티티
	 * @return user_auth 테이블에 유저 정보 저장
	 */
	private UserAuth saveUserAuth(UserRequest.UserCreateRequest dto, User user) {
		UserAuth userAuth = dto.toAuthEntity(user);
		userAuth.encodePassword(passwordEncoder);
		return userAuthRepository.save(userAuth);
	}
	/**
	 * @param dto,user 회원가입 유저 정보, user 엔티티
	 * @return user_social 테이블에 유저 정보 저장
	 */
	private UserSocial saveUserSocial(UserRequest.UserCreateRequest dto, User user) {
		UserSocial userSocial = dto.toSocialEntity(user);
		return userSocialRepository.save(userSocial);
	}

	/**
	 * @param dto user 생성
	 * @return UserResponse.UserCreateResponse
	 */
	@Transactional
	@Override
	public UserResponse.UserCreateResponse create(UserRequest.UserCreateRequest dto) {
		try {
			User user = saveUser(dto);
			UserAuth userAuth = saveUserAuth(dto, user);
			UserSocial userSocial = saveUserSocial(dto, user);

			return UserResponse.UserCreateResponse.from(user,userAuth,userSocial);
		} catch (DataIntegrityViolationException e) {
			throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
		} catch (PersistenceException e) {
			throw new GlobalException(ErrorCode.INVALID_REQUEST_DATA);
		}
	}

	// /**
	//  * @param dto user 수정
	//  * @return UserResponse.UserUpdateResponse
	//  */
	// @Transactional
	// @Override
	// public UserResponse.UserUpdateResponse update(UserRequest.UserUpdateRequest dto) {
	// 	try {
	// 		User user = userRepository.findById(dto.getId())
	// 			.orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
	// 	} catch (DataIntegrityViolationException e) {
	// 		log.warn("사용자 생성 중 무결성 제약 위반: {}", e.getMessage());
	// 		throw new GlobalException(ErrorCode.BAD_REQUEST_SAMPLE_DATA);
	// 	} catch (PersistenceException e) {
	// 		log.error("사용자 생성 중 DB 예외 발생", e);
	// 		throw new GlobalException(ErrorCode.INVALID_REQUEST_DATA);
	// 	}
	// }
}
