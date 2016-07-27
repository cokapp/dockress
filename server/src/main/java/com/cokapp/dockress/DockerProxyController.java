package com.cokapp.dockress;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.ExecStartCmd;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

public class DockerProxyController {

	public void info() {

		DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost("tcp://ct.home:2375").withDockerTlsVerify(false).build();
		
		DockerClient docker = DockerClientBuilder.getInstance(config).build();
		
		
		InfoCmd info = docker.infoCmd();
		
		
		ExecCreateCmdResponse execRsp = docker.execCreateCmd("bash").exec();
		
		ExecStartCmd execStartCmd = docker.execStartCmd(execRsp.getId());

		
		

	}

}
