package src.global.security.jwt.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * jwt 토큰 (Accesstoken, RefreshToken)
 */
@Getter
@RequiredArgsConstructor
public enum TokenType {
	ACCESS("access_token"),

	REFRESH("refresh_token");

	private final String type;
}
