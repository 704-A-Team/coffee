package com.oracle.oBootMybatis01.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// @Component 로 스캔해서 @Aspect 로 등록
@Aspect
@Component
public class LogAop {
	
	// 관심사가 주 프로그램의 어디에 횡단할 것인지를 나타내는 위치
	@Pointcut("within(com.oracle.oBootMybatis01.dao.EmpDao*)")
	private void pointcutMethod()  {
	}
	
	//  target 객체의 메소드 실행전, 후
	@Around("pointcutMethod()")
	public Object loggerAop(ProceedingJoinPoint joinPoint) throws Throwable  {
		// before
		String sigunatureStr = joinPoint.getSignature().toString();
		System.out.println("sigunatureStr-> "+sigunatureStr + "is Start");
		long st = System.currentTimeMillis();
		
		try {
			// 핵심 관심사 (비즈니스) --> totalEmp, listEmp
			Object obj = joinPoint.proceed();
			return obj;
			
		} finally {
			// after
			long et = System.currentTimeMillis();
			System.out.println( sigunatureStr + " is finished");
			System.out.println( sigunatureStr + " 경과 시간 : " + (et - st));
		}
	}
	
	@Before("pointcutMethod()")
	public void beforeMethod() {
		System.out.println("AOP beforeMethod() Start");
	}

}
