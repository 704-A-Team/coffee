package com.oracle.oBootTodoApi01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.oBootTodoApi01.controller.formatter.LocalDateFormatter;
@Configuration
public class CostomServletConfig implements WebMvcConfigurer {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		// TODO Auto-generated method stub
		// WebMvcConfigurer.super.addFormatters(registry);
		registry.addFormatter(new LocalDateFormatter());
		// registry에 등록하는 것이라 Bean 등록하지 않는다
		// 빈은 어디에서 호출할 때 쓴다
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//            * : 모든 폴더  | ** : 서브 폴더까지 모든 폴더
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
				.maxAge(300)
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type")
				;
		// registry에 등록이라 Bean 필요 없다
	}
}
