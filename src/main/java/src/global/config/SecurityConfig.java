package src.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import src.domain.user.constant.UserRole;
import src.global.common.security.exception.CustomAccessDeniedHandler;
import src.global.common.security.exception.CustomAuthenticationEntryPoint;

/**
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;

	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// ✅ 인증, 인가 오류 활성화
			.exceptionHandling(exception -> exception
				.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint))

			// ✅ CSRF 보호 비활성화 (서버에 상태를 저장하지 않기 때문에 CSRF 방어 불필요)
			.csrf(AbstractHttpConfigurer::disable)

			// ✅ 모든 HTTP 요청 조건
			.authorizeHttpRequests(auth ->
				auth
					.requestMatchers("/api/v1/**").permitAll() // 공개 API는 모두 허용
					.requestMatchers("/api/v1/auth/**").authenticated() // 인증된 사용자만 접근 가능
					.requestMatchers("/api/admin/**").hasRole(UserRole.ROLE_ADMIN.getValue()) // ADMIN 역할만 접근 가능
					.anyRequest().denyAll() // 나머지 요청은 모두 거부 (또는 authenticated())
			)

			// .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

			// ✅ 로그인 폼 비활성화 (HTML 페이지 X, API 기반 로그인)
			.formLogin(AbstractHttpConfigurer::disable)

			// ✅ 세션 비활성화 (서버가 사용자 상태를 저장 없이 JWT 적용)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			// ✅ Basic 인증 비활성화 (JWT 사용)
			.httpBasic(AbstractHttpConfigurer::disable);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}