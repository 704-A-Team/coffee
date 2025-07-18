package com.oracle.oBootSecurity01.handler;

import java.io.IOException;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//                                                       로그인 성공 이후에 어떤 URL로 리다이렉트할지 커스터마이징	
	private final RequestCache requestCache = new HttpSessionRequestCache();		 // 로그인 전 사용자가 요청한 url 저장
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // 로그인 후 사용자를 다른 url로 리다이렉트
	
	@Override
	public void onAuthenticationSuccess		// 로긴 성공 시 자동 실행 된다
					(HttpServletRequest request
				   , HttpServletResponse response
				   , Authentication authentication) throws IOException, ServletException {
		
		setDefaultTargetUrl("/"); // 요청 없었으면 홈
		SavedRequest savedRequest = requestCache.getRequest(request, response);		// 인증 성공했을 때 원하는 페이지에 보내주기 위해 캐시에 저장
		if(savedRequest!=null) { // 요청 페이지 존재하면
			String targetUrl = savedRequest.getRedirectUrl();
			System.out.println("FormAuthenticationSuccessHandler targetUrl->" +targetUrl);
			redirectStrategy.sendRedirect(request, response, targetUrl);  // 보내
		} else {
			redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl()); // 요청 없으면 홈
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	
	
}
