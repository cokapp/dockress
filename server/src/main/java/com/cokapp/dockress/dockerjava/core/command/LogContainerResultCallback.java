/*
 * Created on 21.07.2015
 */
package com.cokapp.dockress.dockerjava.core.command;

import javax.websocket.Session;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

public class LogContainerResultCallback extends ResultCallbackTemplate<LogContainerResultCallback, Frame> {
	private Session session;
	
	public LogContainerResultCallback(Session session) {
		this.session = session;
	}
	
	@Override
	public void onNext(Frame frame) {
		if (frame == null) {
			return;
		}

		String next = new String(frame.getPayload());

		switch (frame.getStreamType()) {
		case STDOUT:
		case RAW:
			break;
		case STDERR:
		default:
			next += "*********ERROR*********";
		}

		session.getAsyncRemote().sendText(next);
	}
}
