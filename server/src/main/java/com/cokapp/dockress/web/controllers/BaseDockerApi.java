package com.cokapp.dockress.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.dockerjava.api.DockerClient;

public abstract class BaseDockerApi {
	@Autowired
	protected DockerClient dockerClient;
}
