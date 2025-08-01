package com.oracle.coffee.configuration.km;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("file:///C:/spring/springSrc17/coffee/upload/")
				;
		
		/*
			웹 브라우저가 http://서버주소/upload/어쩌구 이런 식으로 요청하면,
			스프링이 이 요청을 받아서 실제 컴퓨터 내의 특정 폴더(예: C:/spring/springSrc17/coffee/upload/)
			에 있는 파일을 찾아서 응답해 주겠다는 뜻이야.
			
			addResourceHandler("/upload/**")
			→ 웹에서 /upload/ 로 시작하는 모든 요청을 처리하겠다는 의미야.
			→ **는 /upload/ 뒤에 어떤 경로가 와도 모두 포함한다는 뜻이야.
			
			.addResourceLocations("file:///C:/spring/springSrc17/coffee/upload/")
			→ 그 요청에 대응하는 실제 파일 위치(폴더 경로)를 지정해주는 부분!
		 */
	}

}
