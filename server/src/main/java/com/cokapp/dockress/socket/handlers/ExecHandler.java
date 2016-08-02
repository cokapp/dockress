package com.cokapp.dockress.socket.handlers;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.cokapp.dockress.docker.DockerManager;
import com.cokapp.dockress.socket.extend.ExecStartResultCallbackExtend;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;

@ServerEndpoint("/exec")
public class ExecHandler {

	@OnOpen
	public void onOpen(Session session) throws IOException {

		session.setMaxTextMessageBufferSize(1024 * 1024 * 1024);
		session.setMaxIdleTimeout(1000 * 60 * 60);		
		
		try {
			PipedInputStream dest = new PipedInputStream(1024 * 1024);
			PipedOutputStream src = new PipedOutputStream();

			//InputReadThead inputReadThead = new InputReadThead(dest);

			PipedInputStream in = new PipedInputStream(src, 1024 * 1024);
			PipedOutputStream out = new PipedOutputStream(dest);

			session.getUserProperties().put("src", src);
			session.getUserProperties().put("dest", dest);
			//session.getUserProperties().put("inputReadThead", inputReadThead);

			DockerClient dockerClient = DockerManager.getDefault();
			String containerId = "1c27553310d2";

			ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId).withAttachStdout(true)
					.withAttachStdin(true).withTty(true).withCmd("bash").exec();

			dockerClient.execStartCmd(execCreateCmdResponse.getId()).withDetach(false).withStdIn(in)
					.exec(new ExecStartResultCallbackExtend(session));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@OnMessage
	public void onMessage(Session session, String message) {

		try {
			PipedOutputStream src = (PipedOutputStream) session.getUserProperties().get("src");
			//PipedInputStream dest = (PipedInputStream) session.getUserProperties().get("dest");
			//InputReadThead inputReadThead = (InputReadThead) session.getUserProperties().get("inputReadThead");

			System.out.println("send: " + message);

			src.write(message.getBytes());
			src.flush();

			//session.getAsyncRemote().sendText(inputReadThead.getCache());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}