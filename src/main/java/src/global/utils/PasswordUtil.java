package src.global.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
