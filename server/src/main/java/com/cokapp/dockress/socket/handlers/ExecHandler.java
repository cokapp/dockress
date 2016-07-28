package com.cokapp.dockress.socket.handlers;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cokapp.dockress.docker.DockerManager;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.core.command.ExecStartResultCallback;

@Component
public class ExecHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	
		DockerClient dockerClient = DockerManager.getDefault();
		String containerId = "1c27553310d2";

		InputStream stdin = System.in;

		ByteArrayOutputStream stdout = new ByteArrayOutputStream();


		ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId).withAttachStdout(true)
				.withAttachStdin(true).withTty(true).withCmd("bash").exec();

		dockerClient.execStartCmd(execCreateCmdResponse.getId()).withDetach(false).withStdIn(stdin)
				.exec(new ExecStartResultCallback(stdout, System.err));
	
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println(message.getPayload());


		
		
		
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
