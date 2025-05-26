package src.global.common.security.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import src.domain.user.entity.User;

/**
 * PackageName : ssammudan.cotree.global.config.security.user
 * FileName    : CustomUser
 * Author      : hc
 * Date        : 25. 3. 31.
 * Description :
 * =====================================================================================================================
 * DATE          AUTHOR               NOTE
 * ---------------------------------------------------------------------------------------------------------------------
 * 25. 3. 31.     hc               Initial creation
 */
@RequiredArgsConstructor
public class CustomUser implements UserDetails {
	private final User user;

	@Override
	public String getUserEmail() {
		return user.getEmail();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	public String getId() {
		return user.getId();
	}

	public void setLogin() {
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken(this, null, getAuthorities())
		);
	}
}