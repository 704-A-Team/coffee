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

		http
			.csrf(AbstractHttpConfigurer::disable)
			// 인가
        	.authorizeHttpRequests(auth->auth
                    .requestMatchers( "/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*"
                    	         	, "/WEB-INF/views/**").permitAll()
                    //login
                    .requestMatchers("/","/login*","/error*").permitAll()
                    .requestMatchers("/login/findPassword","/login/findPassword/**").permitAll()
                    //client
                    .requestMatchers("/client/clientInForm").hasAnyAuthority("ROLE_USER","ROLE_MANAGER")
                    .requestMatchers("/client/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER")
                    //emp
                    .requestMatchers("/emp/empInForm").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/emp/empDetail**").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/emp/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER")
                    //dept
                    .requestMatchers("/dept/deptInForm").hasAuthority("ROLE_MANAGER")
                    //mypage
                    .requestMatchers("/MyPage/MyPage").authenticated() 
                    //provide
                    .requestMatchers("/provide/provideInForm").hasAuthority("ROLE_MANAGER")
                    //sw
                    .requestMatchers("/sw/wonProductInForm").hasAuthority("ROLE_MANAGER")
                    //km
                    .requestMatchers("/km/productInForm").hasAnyAuthority("ROLE_USER","ROLE_MANAGER")
                    .requestMatchers("/km/mfgInForm").hasAnyAuthority("ROLE_USER","ROLE_MANAGER")
                    .requestMatchers("/km/mfgReportList").hasAnyAuthority("ROLE_USER","ROLE_MANAGER")
                    .requestMatchers("/km/wanList").hasAnyAuthority("ROLE_USER","ROLE_MANAGER")
                    .requestMatchers("/km/mfgList").hasAnyAuthority("ROLE_USER","ROLE_MANAGER")
                    //board
                    .requestMatchers("/board/boardEditForm/**").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/board/boardWriteForm").hasAnyAuthority("ROLE_USER","ROLE_MANAGER")
                    //order
                    .requestMatchers("/order/new").hasAuthority("ROLE_CLIENT2")
                    .requestMatchers("/order/approve/**").hasAuthority("ROLE_MANAGER")
                    .requestMatchers("/order/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_CLIENT2")
                    //inventory
                    .requestMatchers("/inventory/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER")
                   
                    
                    
                    //Login 불필요
                    .requestMatchers("/sw/**").permitAll()                    
                    //.requestMatchers("/km/**").permitAll()
                    .requestMatchers("/board/**").permitAll()
                    
				    .anyRequest().authenticated()
				  	
					)
			
        	.formLogin(form -> form
        		    .loginPage("/login").permitAll()
        		    .loginProcessingUrl("/login")            
        		    .authenticationDetailsSource(authenticationDetailsSource)
        		    .defaultSuccessUrl("/", false) 
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
