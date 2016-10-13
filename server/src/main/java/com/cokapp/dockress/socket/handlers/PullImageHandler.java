package com.cokapp.dockress.socket.handlers;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cokapp.dockress.docker.PullImageProgressManager;
import com.cokapp.dockress.dockerjava.core.command.PullImageResultCallback;

@ServerEndpoint("/ws/pull")
public class PullImageHandler extends BaseDockerHandler {
	public static Logger logger = LoggerFactory.getLogger(PullImageHandler.class);

	@OnOpen
	public void onOpen(Session session) throws IOException {
		session.setMaxTextMessageBufferSize(1024 * 1024 * 1024);
		session.setMaxIdleTimeout(1000 * 60 * 60);
				
		session.getAsyncRemote().sendText(PullImageProgressManager.getJson());
	}

	@OnMessage
	public void onMessage(Session session, String repository) {
		PullImageResultCallback pullImageResultCallback = new PullImageResultCallback(session);
		getDockerClient().pullImageCmd(repository).exec(pullImageResultCallback);
	}
	
	
}
