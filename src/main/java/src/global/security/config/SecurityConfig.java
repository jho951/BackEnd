package src.global.security.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import src.domain.user.constant.UserRole;
import src.global.security.filter.JwtAuthenticationFilter;
import src.global.security.exception.CustomAccessDeniedHandler;
import src.global.security.exception.CustomAuthenticationEntryPoint;

/**
 * Spring Security의 전반적인 보안 설정을 구성하는 클래스입니다.
 * JWT 기반 인증 방식을 사용하며, 세션을 사용하지 않고 Stateless 아키텍처로 구성됩니다.
 * 인증 및 인가 실패에 대한 커스텀 핸들러와 필터를 설정합니다.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	/**
	 * JWT 토큰의 유효성을 검증하고 인증을 수행하는 커스텀 필터입니다.
	 * {@link UsernamePasswordAuthenticationFilter} 앞에 등록됩니다.
	 */
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	/**
	 * 인가 실패(403 Forbidden) 시 처리하는 핸들러입니다.
	 */
	private final CustomAccessDeniedHandler accessDeniedHandler;

	/**
	 * 인증 실패(401 Unauthorized) 시 처리하는 핸들러입니다.
	 */
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	/**
	 * Spring Security의 핵심 보안 필터 체인을 구성합니다.
	 * - JWT 필터 등록
	 * - 세션 미사용 설정 (STATELESS)
	 * - Form 로그인, Basic 인증, CSRF 보호 비활성화
	 * - 경로별 접근 권한 설정
	 * - 예외 핸들러 설정
	 *
	 * @param http {@link HttpSecurity} 보안 설정 빌더
	 * @return 구성된 {@link SecurityFilterChain}
	 * @throws Exception 설정 중 예외 발생 시
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth ->
				auth
					.requestMatchers(
						"/v3/api-docs/**",
						"/swagger-ui/**",
						"/swagger-ui.html",
						"/swagger-resources/**",
						"/webjars/**"
					).permitAll()
					.requestMatchers("/api/v1/auth/**").authenticated()
					.requestMatchers("/api/admin/**").hasRole(UserRole.ROLE_ADMIN.getValue())
					.requestMatchers("/api/v1/**").permitAll()
					.anyRequest().denyAll()
			)
			.exceptionHandling(exception -> exception
				.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint));

		return http.build();
	}

	/**
	 * {@link AuthenticationManager} Bean을 주입합니다.
	 * 인증 과정에서 사용되며, {@code AuthenticationProvider}를 통해 실제 인증 로직이 수행됩니다.
	 *
	 * @param configuration Spring Security의 인증 설정 정보
	 * @return 인증 매니저 객체
	 * @throws Exception 인증 매니저 생성 중 예외 발생 시
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	/**
	 * 비밀번호를 암호화하는 {@link PasswordEncoder} Bean입니다.
	 * Spring Security의 권장 알고리즘인 BCrypt를 사용합니다.
	 *
	 * @return {@link BCryptPasswordEncoder} 인스턴스
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
