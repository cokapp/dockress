package com.cokapp.dockress.web.controllers;

import com.cokapp.dockress.docker.DockerManager;
import com.github.dockerjava.api.DockerClient;

public abstract class BaseDockerApi {
	protected DockerClient getDockerClient() {
		return DockerManager.getDefault();
	}
}
