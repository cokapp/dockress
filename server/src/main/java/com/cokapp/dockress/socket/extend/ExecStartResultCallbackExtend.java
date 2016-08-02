package com.cokapp.dockress.socket.extend;

import javax.websocket.Session;

import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.ExecStartResultCallback;

public class ExecStartResultCallbackExtend extends ExecStartResultCallback {

	private Session session;

	public ExecStartResultCallbackExtend(Session session) {
		this.session = session;
	}

	@Override
	public void onNext(Frame frame) {
		if (frame == null) {
			return;
		}

		String next = new String(frame.getPayload());

		System.out.println("===============next: " + next);

		session.getAsyncRemote().sendText(next);

	}

}
