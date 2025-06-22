package src.global.security.user;

import java.util.List;
import java.util.UUID;
import java.util.Collection;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import src.domain.user.entity.User;
import src.global.security.auth.entity.Auth;

/**
 * Spring Security의 인증 객체로 사용될 CustomUser 클래스입니다.
 * <p>
 * {@link User}와 {@link Auth} 엔티티 정보를 기반으로
 * 인증 및 권한 정보를 제공합니다.
 */
@RequiredArgsConstructor
public class CustomUser implements UserDetails {

	private final User user;
	private final Auth auth;

	/**
	 * 사용자 UUID 반환
	 * @return 사용자 ID
	 */
	public UUID getId() {
		return user.getId();
	}

	/**
	 * 사용자 이메일 반환
	 * @return 이메일 (username)
	 */
	public String getUserEmail() {
		return user.getEmail();
	}

	@Override
	public String getPassword() {
		return auth.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(user.getRole().name()));
	}

	/**
	 * 현재 유저를 SecurityContextHolder에 등록하여 로그인 처리합니다.
	 * JWT 인증 후 수동 인증이 필요할 경우 호출합니다.
	 */
	public void setLogin() {
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(this, null, getAuthorities())
		);
	}

}
