package src.global.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordUtilTest {
	/**
	 * 상황 : 입력한 비밀번호가 같더라도 서로 다른 해시를 생성해야 합니다.
	 * 목적 : 해시의 무작위성 겁증
	 */
	@Test
	void encode_ShouldReturnDifferentHashForSameInput() {
		String rawPassword = "password123";

		String hash1 = PasswordUtil.encode(rawPassword);
		String hash2 = PasswordUtil.encode(rawPassword);

		assertNotEquals(hash1, hash2, "동일한 입력에 대해 다른 해시 값이 제공되었습니다.");
	}

	/**
	 * 상황: 입력한 비밀번호가 원래 비밀번호로부터 생성된 해시와 일치할 경우, 인증에 성공해야합니다.
	 * 목적: 해시를 통한 비밀번호 일치 확인
	 */
	@Test
	void matches_ShouldReturnTrueForCorrectPassword() {
		String rawPassword = "password123";
		String hashedPassword = PasswordUtil.encode(rawPassword);

		assertTrue(PasswordUtil.matches(rawPassword, hashedPassword), "입력한 비밀번호가 해시와 일치합니다.");
	}

	/**
	 * 상황 : 잘못된 비밀번호가 주어졌을 때, 해시된 비밀번호와 일치하지 않아야 하며, 인증에 실패해야 합니다.
	 * 목적 : 해시를 통한 비밀번호 불일치 확인
	 */
	@Test
	void matches_ShouldReturnFalseForIncorrectPassword() {
		String rawPassword = "password123";
		String hashedPassword = PasswordUtil.encode(rawPassword);

		assertFalse(PasswordUtil.matches("wrongPassword", hashedPassword), "잘못된 비밀번호는 해시와 일치하지 않습니다.");
	}
}
