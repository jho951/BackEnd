package src.global.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PasswordUtilTest {

	@Autowired
	private PasswordUtil passwordUtil;

	@Test
	void encode_ShouldReturnDifferentHashForSameInput() {
		// given
		String rawPassword = "securePassword123";

		// when
		String hash1 = passwordUtil.encode(rawPassword);
		String hash2 = passwordUtil.encode(rawPassword);

		// then
		assertNotEquals(hash1, hash2, "같은 입력값이라도 해시 결과는 달라야 한다.");
	}

	@Test
	void matches_ShouldReturnTrueWhenPasswordMatchesHash() {
		// given
		String rawPassword = "securePassword123";
		String hashedPassword = passwordUtil.encode(rawPassword);

		// when
		boolean result = passwordUtil.matches(rawPassword, hashedPassword);

		// then
		assertTrue(result, "비밀번호가 해시와 일치해야 한다.");
	}

	@Test
	void matches_ShouldReturnFalseWhenPasswordDoesNotMatchHash() {
		// given
		String rawPassword = "securePassword123";
		String wrongPassword = "wrongPassword";
		String hashedPassword = passwordUtil.encode(rawPassword);

		// when
		boolean result = passwordUtil.matches(wrongPassword, hashedPassword);

		// then
		assertFalse(result, "비밀번호가 다르면 해시와 일치하지 않아야 한다.");
	}
}
