package com.cokapp.dockress.dockerjava.core.async;

import javax.websocket.Session;

import com.cokapp.cokits.util.mapper.JsonMapper;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

public class StatsResultCallback extends ResultCallbackTemplate<StatsResultCallback, Statistics> {
	private Session session;

	private final static String SESSION_KEY = "stats-result";

	public StatsResultCallback(Session session) {
		this.session = session;
		this.session.getUserProperties().put(SESSION_KEY, this);
	}

	@Override
	public void onNext(Statistics statis) {
		String statisJson = JsonMapper.nonDefaultMapper().toJson(statis);
		session.getAsyncRemote().sendText(statisJson);
	}

	public static StatsResultCallback getInstance(Session session) {
		return (StatsResultCallback) session.getUserProperties().get(SESSION_KEY);
	}

}