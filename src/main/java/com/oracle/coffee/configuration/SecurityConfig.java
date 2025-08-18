package com.oracle.coffee.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.oracle.coffee.security.handler.FormAccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthenticationProvider	authenticationProvider;
	private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> 
	                     authenticationDetailsSource;
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
                    .requestMatchers("/","/login*","/error*").permitAll()
                    .requestMatchers("/client/clientInForm").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/emp/empInForm").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/dept/deptInForm").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/MyPage/MyPage").authenticated() //마이페이지는 로그인 필요
                    
                    .requestMatchers("/provide/provideInForm").hasAuthority("ROLE_MANAGER")
                    
                    .requestMatchers("/sw/wonProductInForm").hasAuthority("ROLE_MANAGER")
                    
                    
                    
                    
                    //통합기간중에만 login불필요
                    .requestMatchers("/jh/**").permitAll()
                    .requestMatchers("/sw/**").permitAll()
                    .requestMatchers("/provide/**").permitAll()
                    .requestMatchers("/km/**").permitAll()
                    .requestMatchers("/order/**").permitAll()
                    .requestMatchers("/inventory/**").permitAll()
						
                    
                    
                    
				    .anyRequest().authenticated()
				  	
					)
			
			// 인증
        	.formLogin(form -> form
        		    .loginPage("/login").permitAll()
        		    .loginProcessingUrl("/login")            
        		    .authenticationDetailsSource(authenticationDetailsSource)
        		    .defaultSuccessUrl("/", false) // false=저장된 요청 있으면 그리로, 없으면 여기로
        		    .failureHandler(failureHandler)
        		    .permitAll()
        		)


            .authenticationProvider(authenticationProvider)
            .exceptionHandling(
         		   exception -> exception
                                     .accessDeniedHandler(new FormAccessDeniedHandler("/denied"))
                              )
             ;

  		
 		
		return http.build();
	}
	
}
