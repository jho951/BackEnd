package src.global.security.user;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import src.domain.user.entity.User;
import src.global.response.constant.ErrorCode;
import src.global.exception.GlobalException;
import src.domain.user.repository.UserRepository;
import src.global.security.jwt.AccessTokenService;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	private final AccessTokenService accessTokenService;

	/**
	 * 일반 로그인
	 * 이 CustomUser는 UserDetails를 구현한 객체여야 하며, 인증 객체에 사용됩니다.
	 * @param name 식별 가능한 이름
	 * @return CustomUser
	 * @throws UsernameNotFoundException 유저를 찾을 수 없을 때
	 */
	@Override
	public CustomUser loadUserByUsername(String name) throws UsernameNotFoundException {
		// name을 통해 user 조회
		User user = userRepository.findUserByName(name)
			.orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_USER));
		// 조회된 Member를 CustomUser 객체로 래핑하여 반환합니다.
		return new CustomUser(user, null);
	}

}