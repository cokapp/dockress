package com.cokapp.dockress.socket.handlers;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.cokapp.dockress.socket.extend.ExecStartResultCallbackExtend;
import com.github.dockerjava.api.command.AttachContainerCmd;

@ServerEndpoint("/attach/{containerId}")
public class AttachHandler extends BaseDockerHandler{

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "containerId") String containerId) throws IOException {
		session.setMaxTextMessageBufferSize(1024 * 1024 * 1024);
		session.setMaxIdleTimeout(1000 * 60 * 60);
		try {
			PipedOutputStream src = new PipedOutputStream();
			PipedInputStream in = new PipedInputStream(src);
			session.getUserProperties().put("src", src);

			AttachContainerCmd attachContainerCmd = this.getDockerClient().attachContainerCmd(containerId).withStdIn(in);		
			attachContainerCmd.exec(new ExecStartResultCallbackExtend(session));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}
