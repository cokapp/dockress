package com.cokapp.dockress.socket.handlers;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.cokapp.dockress.dockerjava.core.async.StatsResultCallback;
import com.cokapp.dockress.dockerjava.core.command.LogContainerResultCallback;

@ServerEndpoint("/ws/log/{containerId}")
public class LogHandler extends BaseDockerHandler {
	
	@OnOpen
	public void onOpen(Session session, @PathParam(value = "containerId") String containerId) throws IOException {
		session.setMaxTextMessageBufferSize(1024 * 1024 * 1024);
		session.setMaxIdleTimeout(1000 * 60 * 60);
		try {
			this.getDockerClient().logContainerCmd(containerId)
            .withStdErr(true)
            .withStdOut(true)
            .withFollowStream(true)
            .withTailAll()
			.exec(new LogContainerResultCallback(session));		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(Session session) throws IOException {
		try {
			StatsResultCallback.getInstance(session).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
