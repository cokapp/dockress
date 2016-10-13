package com.cokapp.dockress.dockerjava.core.command;

import javax.websocket.Session;

import com.cokapp.dockress.docker.PullImageProgressManager;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

public class PullImageResultCallback extends ResultCallbackTemplate<PullImageResultCallback, PullResponseItem> {
	private Session session;

	public PullImageResultCallback(Session session) {
		this.session = session;
	}

	@Override
	public void onNext(PullResponseItem item) {
		if (item.isPullSuccessIndicated()) {
			PullImageProgressManager.removeProgress(item.getId());
		} else {
			if (item.getProgressDetail() != null) {
				PullImageProgressManager.addProgress(item.getId(), item.getProgressDetail());
			}
		}
		session.getAsyncRemote().sendText(PullImageProgressManager.getJson());
	}
}
