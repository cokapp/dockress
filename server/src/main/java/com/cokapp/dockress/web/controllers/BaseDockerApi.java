package com.cokapp.dockress.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.dockerjava.api.DockerClient;

public class BaseDockerApi {
	@Autowired
	protected DockerClient dockerClient;
}
