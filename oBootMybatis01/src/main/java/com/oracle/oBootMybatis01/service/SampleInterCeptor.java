package com.oracle.oBootMybatis01.service;



import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SampleInterCeptor implements HandlerInterceptor {
	public SampleInterCeptor() {
		// TODO Auto-generated constructor stub
	}
	
	
	// 3번
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 	컨트롤러에서 ID를 받아서 DB에 회원 있는지 검사 (memCnt)
		//  "DB에 존재하냐 검사"는 postHandle에서 하는 게 맞아
		
		System.out.println("post handle");
		String ID = (String) modelAndView.getModel().get("id");
		int memCnt = (Integer) modelAndView.getModel().get("memCnt");
		System.out.println("SampleInterCeptor post Handle memCnt: "+memCnt);
		if(memCnt < 1 ) {
			System.out.println("memCnt Not exists");
			request.getSession().setAttribute("ID", ID);
			// User가 존재하지 않으면 User interCeptor Page(회원등록) 이동
			response.sendRedirect("doMemberWrite");
		} else {
			System.out.println("memCnt exists");
			request.getSession().setAttribute("ID", ID);
			// User가 존재하면 User interCeptor Page(회원List) 이동
			response.sendRedirect("doMemberList");
		}
	}
	
	// 1번
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("pre handle");
		HandlerMethod method = (HandlerMethod) handler;
		Method methodObj = method.getMethod();
		System.out.println("Bean: " + method.getBean());	// 실행될 컨트롤러의 객체 자체를 리턴
		System.out.println("Method: " + methodObj);			// 실행될 컨트롤러의 메서드 정보
		return true;
	}
}
