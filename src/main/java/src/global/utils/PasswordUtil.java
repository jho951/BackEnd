package src.global.utils;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class PasswordUtil {
	private static PasswordEncoder passwordEncoder;

	public PasswordUtil(PasswordEncoder passwordEncoder) {
		PasswordUtil.passwordEncoder = passwordEncoder;
	}

	/**
	 * 평문 비밀번호를 BCrypt 해시로 인코딩합니다.
	 *
	 * @param rawPassword 평문 비밀번호
	 * @return 해시된 비밀번호
	 */
	public static String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	/**
	 * 입력한 평문 비밀번호와 해시가 일치하는지 비교합니다.
	 *
	 * @param rawPassword 평문 비밀번호
	 * @param encodedPassword 해시된 비밀번호
	 * @return 일치 여부
	 */
	public static boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
