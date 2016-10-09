package com.cokapp.dockress.docker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.Version;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.netty.DockerCmdExecFactoryImpl;

/**
 * Docker客户端工厂
 * 
 * @Description: TODO
 * @Copyright: Copyright (c) 2015
 * @Company:杭州爱财网络科技有限公司
 * @author heichengliang@aixuedai.com
 * @version V1.0.0
 * @since JDK 1.7
 * @date 2016年8月3日 下午4:41:46
 *
 */
public class DockerClientFactory {
	public static Logger logger = LoggerFactory.getLogger(DockerClientFactory.class);

	public DockerClient buildDefault() {
		DockerCmdExecFactory dockerCmdExecFactory = new DockerCmdExecFactoryImpl();
		DockerClient dockerClient = DockerClientBuilder.getInstance().withDockerCmdExecFactory(dockerCmdExecFactory)
				.build();
		try {
			Version version = dockerClient.versionCmd().exec();
			logger.debug("version: {}", version.toString());
			logger.debug("docker client inited!");
		} catch (Exception e) {
			logger.error("docker client init failed: \"{}\".", e.getMessage());
		}

		logger.debug("docker client inited!");
		
		return dockerClient;
	}

}
