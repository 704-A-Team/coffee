package com.oracle.coffee.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	   // 업로드 파일 경로를 application.properties에서 읽어옵니다.
	   @Value("${com.oracle.coffee.upload.path}")
	   private String uploadPath;
	    
	   @Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {
	       registry.addResourceHandler("/upload/**")
	               .addResourceLocations("file:///" + System.getProperty("user.dir") + "/" + uploadPath + "/");
	   }
}
