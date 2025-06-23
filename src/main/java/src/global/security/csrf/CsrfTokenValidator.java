package src.global.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

@Component
public class CsrfTokenValidator {

	public boolean isValid(HttpServletRequest request) {
		CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");

		String actualToken = request.getHeader("X-CSRF-TOKEN");
		return csrfToken != null && csrfToken.getToken().equals(actualToken);
	}
}
