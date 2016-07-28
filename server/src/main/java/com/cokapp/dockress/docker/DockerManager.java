package com.cokapp.dockress.docker;

import java.util.Map;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.netty.DockerCmdExecFactoryImpl;

import jersey.repackaged.com.google.common.collect.Maps;

public class DockerManager {

	private static Map<String, DockerClient> CLIENTS = Maps.newConcurrentMap();

	static {
		DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://ct.home:2375")
				.withDockerTlsVerify(false).build();
		DockerCmdExecFactory dockerCmdExecFactory = new DockerCmdExecFactoryImpl();
		DockerClient dockerClient = DockerClientBuilder.getInstance(config)
				.withDockerCmdExecFactory(dockerCmdExecFactory).build();

		CLIENTS.put("default", dockerClient);
	}

	public static DockerClient getDefault() {
		return CLIENTS.get("default");
	}

}
