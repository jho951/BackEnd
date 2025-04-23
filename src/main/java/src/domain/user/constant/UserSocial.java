package src.domain.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserSocial {
	GOOGLE("구글"),
	KAKAO("카카오"),
	DEFAULT("기본");

	private final String value;
}
