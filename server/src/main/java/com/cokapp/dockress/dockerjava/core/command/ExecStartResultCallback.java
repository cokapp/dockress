package com.cokapp.dockress.dockerjava.core.command;

import javax.websocket.Session;

import com.github.dockerjava.api.model.Frame;

public class ExecStartResultCallback extends com.github.dockerjava.core.command.ExecStartResultCallback {

	private Session session;

	public ExecStartResultCallback(Session session) {
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
