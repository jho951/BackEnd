package src.global.common.security.user;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import src.domain.user.entity.User;
import src.domain.user.entity.UserAuth;

/**
 * PackageName : src.global.common.security.user
 * FileName    : CustomUser
 * Author      : jh
 * Date        : 25. 5. 27.
 * Description : security user
 */

@RequiredArgsConstructor
public class CustomUser implements UserDetails {
	private final User user;
	private final UserAuth userAuth;

	public UUID getId() {
		return user.getId();
	}

	public String getUserEmail() {
		return user.getEmail();
	}

	@Override
	public String getPassword() {
		return userAuth.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setLogin() {
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(this, null, getAuthorities())
		);
	}
}
