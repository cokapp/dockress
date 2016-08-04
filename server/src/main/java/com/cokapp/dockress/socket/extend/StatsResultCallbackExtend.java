package com.cokapp.dockress.socket.extend;

import javax.websocket.Session;

import com.cokapp.cokits.util.mapper.JsonMapper;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

public class StatsResultCallbackExtend extends ResultCallbackTemplate<StatsResultCallbackExtend, Statistics> {
	private Session session;

	private final static String SESSION_KEY = "stats-result";

	public StatsResultCallbackExtend(Session session) {
		this.session = session;
		this.session.getUserProperties().put(SESSION_KEY, this);
	}

	@Override
	public void onNext(Statistics statis) {
		String statisJson = JsonMapper.nonDefaultMapper().toJson(statis);
		session.getAsyncRemote().sendText(statisJson);
	}

	public static StatsResultCallbackExtend getInstance(Session session) {
		return (StatsResultCallbackExtend) session.getUserProperties().get(SESSION_KEY);
	}

}