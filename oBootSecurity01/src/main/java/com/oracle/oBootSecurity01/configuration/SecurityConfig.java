package com.oracle.oBootSecurity01.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.oracle.oBootSecurity01.handler.FormAccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AuthenticationProvider authenticationProvider;
	private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
	private final AuthenticationSuccessHandler successHandler;
	private final AuthenticationFailureHandler failureHandler;
	private final UserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//  :: -> 메소드 참조라고 하며 람다식에서 불필요한 매개변수를 제거하는 것이 목적
		http
			.csrf(AbstractHttpConfigurer::disable)
			// 인가
        	.authorizeHttpRequests(auth->auth
                    .requestMatchers( "/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*"
                    	         	, "/WEB-INF/views/**").permitAll()
                    .requestMatchers("/","/signup","/login*","/error*").permitAll()
                    .requestMatchers("/user").hasAuthority("ROLE_USER")
                    .requestMatchers("/manager").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/admanager")
                      .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('MANAGER')"))

                    .anyRequest().authenticated()
					)
			// 인증
			.formLogin(form-> form
					.loginPage("/login") // UsernamePasswordAuthenticationFilter 생성 폼방식의 인증처리(아이디, 비번 비교해준다)
					.authenticationDetailsSource(authenticationDetailsSource)
					.successHandler(successHandler)
					.failureHandler(failureHandler)
					.permitAll()
					) 
		//	.userDetailsService(userDetailsService)
			.authenticationProvider(authenticationProvider)
			.exceptionHandling(
				exception -> exception
						.accessDeniedHandler(new FormAccessDeniedHandler("/denied"))
				)
			;
		return http.build();
	}
	
}
