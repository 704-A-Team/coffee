package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.oBootMybatis01.service.SampleInterCeptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Override                   // 인터셉터 등록자
	public void addInterceptors(InterceptorRegistry registry) {
		// 누군가 URL /interCeptor --> SampleInterceptor() 처리 해줌
		registry.addInterceptor(new SampleInterCeptor())
				.addPathPatterns("/interCeptor")
				;
	}
}
