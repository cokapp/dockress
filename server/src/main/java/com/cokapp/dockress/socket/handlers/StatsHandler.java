package com.cokapp.dockress.socket.handlers;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.cokapp.dockress.dockerjava.core.async.StatsResultCallback;

@ServerEndpoint("/stats/{containerId}")
public class StatsHandler extends BaseDockerHandler {

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "containerId") String containerId) throws IOException {
		session.setMaxTextMessageBufferSize(1024 * 1024 * 1024);
		session.setMaxIdleTimeout(1000 * 60 * 60);
		try {
			this.getDockerClient().statsCmd(containerId).exec(new StatsResultCallback(session));		
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