package com.cokapp.dockress.socket.handlers;

import com.cokapp.dockress.docker.DockerManager;
import com.github.dockerjava.api.DockerClient;

public abstract class BaseDockerHandler {
	protected DockerClient getDockerClient() {
		return DockerManager.getDefault();
	}
}
