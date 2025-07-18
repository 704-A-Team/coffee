package com.oracle.oBootSecurity01.handler;

import java.io.IOException;

import javax.crypto.SecretKey;
import javax.security.auth.login.CredentialExpiredException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(
							HttpServletRequest request
						  , HttpServletResponse response
						  , AuthenticationException exception) throws IOException, ServletException {
		
		String errorMessage = "Invalid Username or Password";
		
		if(exception instanceof BadCredentialsException) {
			errorMessage = "Invaild Password";
		} else if(exception instanceof UsernameNotFoundException) {
			errorMessage = "User not exists";
		} else if(exception instanceof CredentialsExpiredException) {
			errorMessage = "Expired password";
		} else if(exception instanceof SecretKey) {
			errorMessage = "Invalid Secret key"; // 암호키 받았는지 확인
		} 
		
		setDefaultFailureUrl("/login?error=true&exception=" +errorMessage);
		System.out.println("onAuthenticationFailure exception->"+exception);
		super.onAuthenticationFailure(request, response, exception);
	}
	
}
