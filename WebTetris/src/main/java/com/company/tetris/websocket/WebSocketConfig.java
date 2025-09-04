package com.company.tetris.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration @EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{ 
	
	// 메세지 브로커 설정
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.setApplicationDestinationPrefixes("/app");
		config.enableSimpleBroker("/topic", "/queue");
		//애플리케이션에서 처리할 메시지의 접두사. 
		//클라이언트가 이 접두사를 사용하여 메시지를 보내면 해당 메시지는 애플리케이션의 @MessageMapping이나 @SubscribeMapping으로 매핑된 메서드로 라우팅	
	}
	
	//클라이언트가 웹소켓 서버에 연결할 수 있는 엔드포인트 등록
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint("/ws")	//ws 경로로 웹소켓 연결을 위한 엔드포인트
			.setAllowedOriginPatterns("*") //모든 도메인에 크로슨 오리진 요청 허용
			.withSockJS(); // SockJs를 사용하여 웹소켓을 지원하지 않는 브라우저도 연결가능하게
	}

}
