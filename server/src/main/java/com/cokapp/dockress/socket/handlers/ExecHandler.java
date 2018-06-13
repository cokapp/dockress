package com.cokapp.dockress.socket.handlers;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.cokapp.dockress.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;

/**
 * 进入容器
 * 
 * @Description: TODO
 * @Copyright: Copyright (c) 2015
 * @version V1.0.0
 * @since JDK 1.7
 * @date 2016年8月3日 上午10:14:08
 *
 */
@ServerEndpoint("/ws/exec/{containerId}/{cmd}")
public class ExecHandler extends BaseDockerHandler {

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "containerId") String containerId,
			@PathParam(value = "cmd") String cmd) throws IOException {
		session.setMaxTextMessageBufferSize(1024 * 1024 * 1024);
		session.setMaxIdleTimeout(1000 * 60 * 60);
		try {
			PipedOutputStream src = new PipedOutputStream();
			PipedInputStream in = new PipedInputStream(src);
			session.getUserProperties().put("src", src);

			ExecCreateCmdResponse execCreateCmdResponse = this.getDockerClient().execCreateCmd(containerId).withAttachStdout(true)
					.withAttachStdin(true).withTty(true).withCmd(cmd).exec();

			this.getDockerClient().execStartCmd(execCreateCmdResponse.getId()).withDetach(false).withStdIn(in)
					.exec(new ExecStartResultCallback(session));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		try {
			PipedOutputStream src = (PipedOutputStream) session.getUserProperties().get("src");
			src.write(message.getBytes());
			src.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}