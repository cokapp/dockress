package com.cokapp.dockress.docker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.netty.DockerCmdExecFactoryImpl;

import lombok.Getter;

@Getter
public class DockerBuilderTask implements Runnable {
	public static Logger logger = LoggerFactory.getLogger(DockerBuilderTask.class);

	private static final String ERROR_VERSION = "-1";

	private DockerClient dockerClient;

	private String dockerVersion;

	@Override
	public void run() {
		logger.debug("=============start build docker client!=============");

		DockerCmdExecFactory dockerCmdExecFactory = new DockerCmdExecFactoryImpl();
		dockerClient = DockerClientBuilder.getInstance().withDockerCmdExecFactory(dockerCmdExecFactory).build();
		try {
			dockerVersion = dockerClient.versionCmd().exec().getApiVersion();
			logger.debug("docker client build successful!");
		} catch (Throwable e) {
			dockerVersion = ERROR_VERSION;
			logger.error("docker client build failed: \"{}\".", e.getMessage());
		}
		logger.debug("version: {}", dockerVersion);

		logger.debug("=============end build docker client!=============");
	}

	public boolean isReady() {
		return dockerVersion != null && dockerVersion != ERROR_VERSION;
	}

}