package src.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 */

@Configuration // 해당 클래스가 Spring의 설정 클래스임을 나타냅니다.
@EnableWebSecurity // Spring Security를 활성화하고 웹 보안 설정을 구성할 수 있도록 합니다.
public class SecurityConfig {

	// `HttpSecurity`를 통해 구체적인 보안 규칙을 설정합니다.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 로그인 페이지는 인증 없이 접근할 수 있도록 허용합니다.
		http
			// HTTP 요청에 대한 접근 권한을 설정합니다.
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().authenticated() // 모든 요청에 대해 인증 필요
			)
			// 폼 기반 로그인을 설정합니다.
			.formLogin(AbstractAuthenticationFilterConfigurer::permitAll // 로그인 페이지는 모든 사용자 접근 허용
			);
		return http.build();
	}
}