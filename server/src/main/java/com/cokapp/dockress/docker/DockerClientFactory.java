package com.cokapp.dockress.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.netty.DockerCmdExecFactoryImpl;

/**
 * Docker客户端工厂
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
	
	public DockerClient buildDefault() {
		DockerCmdExecFactory dockerCmdExecFactory = new DockerCmdExecFactoryImpl();
		DockerClient dockerClient = DockerClientBuilder.getInstance().withDockerCmdExecFactory(dockerCmdExecFactory)
				.build();
		return dockerClient;
	}

}
