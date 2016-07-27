package com.cokapp.dockress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.github.dockerjava.api.DockerClient;

import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.ExecStartCmd;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.netty.DockerCmdExecFactoryImpl;

public class DockerProxyTest {

	@Test
	public void infoTest() throws InterruptedException, IOException {

		DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://ct.home:2375")
				.withDockerTlsVerify(false).build();

		DockerCmdExecFactory dockerCmdExecFactory = new DockerCmdExecFactoryImpl();
		DockerClient dockerClient = DockerClientBuilder.getInstance(config)
				.withDockerCmdExecFactory(dockerCmdExecFactory).build();

		String containerId = "1c27553310d2";

		//ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId).withAttachStdout(true)
		//		.withAttachStdout(true).withAttachStderr(true).withCmd("bash").exec();

		//ExecStartResultCallback cb = new ExecStartResultCallback(System.out, System.err);

		//ExecStartCmd startCmd = dockerClient.execStartCmd(execCreateCmdResponse.getId());
		//InputStream input = startCmd.getStdin();
		//startCmd.withDetach(true).withTty(true).exec(cb).awaitCompletion();
		
		
        InputStream stdin = new ByteArrayInputStream("ls\n".getBytes());

        ByteArrayOutputStream stdout = new ByteArrayOutputStream();

        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                .withAttachStdout(true)
                .withAttachStdin(true)
                .withTty(false)
                .withCmd("bash").exec();

        dockerClient.execStartCmd(execCreateCmdResponse.getId())
                .withDetach(false)
                .withStdIn(stdin)
                .exec(new ExecStartResultCallback(stdout, System.err));
		
		String xx = stdout.toString();

		System.out.println(xx);

	}

}
