package com.company.tetris.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration @EnableWebMvc
public class WebConfig implements WebMvcConfigurer{ // 스프링 MVC 웹 설정 커스터마이징
	
	//정적리소스(html,css,javascript 파일등)에대한 요청을 처리하기 위한 리소스 핸들러 추가
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {     
		registry.addResourceHandler("/**") //모든 경로에 들어올 때 사용할 리소스 핸들러 설정
				.addResourceLocations("classpath:/static/"); //정적 리소스가 위치한 디렉터리 지정
	}
	
	//CORS 정책 설정
	@Override
	public void addCorsMappings(CorsRegistry registry) {  
		registry.addMapping("/**").allowedOrigins("*"); 
		//모든경로에 대한 요청에 대해 모든 도메인(*)의 접근 허용(개발환경에서 유용할 수 있으나 보안상의 이유로 실제 프로덕션 환경에서는 특정 도메인에 대해서만 접근 허용하도록 제한)
	}

}
