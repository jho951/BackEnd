package src.domain.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	ROLE_USER("회원"),
	ROLE_ADMIN("관리자");

	private final String value;
}
