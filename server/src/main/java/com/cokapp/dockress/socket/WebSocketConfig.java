package com.cokapp.dockress.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cokapp.dockress.socket.handlers.ExecHandler;

@Component
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Autowired
	private ExecHandler execHandler;
	@Autowired
	private HandShake handShake;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(execHandler, "/exec").addInterceptors(handShake);
	}

}