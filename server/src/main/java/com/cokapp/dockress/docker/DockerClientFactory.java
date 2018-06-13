package com.cokapp.dockress.docker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cokapp.cokits.core.lang.exception.ExceptionUtils;
import com.github.dockerjava.api.DockerClient;

/**
 * Docker客户端工厂
 * 
 * @Description: TODO
 * @Copyright: Copyright (c) 2015
 * @version V1.0.0
 * @since JDK 1.7
 * @date 2016年8月3日 下午4:41:46
 *
 */
public class DockerClientFactory {
	public static Logger logger = LoggerFactory.getLogger(DockerClientFactory.class);

	public DockerClient buildDefault() throws InterruptedException {
		//step1. 新线程构建客户端
		DockerBuilderTask task = new DockerBuilderTask();
		Thread thread = new Thread(task);
		thread.start();
		
		//step2. 等待最多10秒
		int second = 0;
		while(second < 10 && !task.isReady()){
			Thread.sleep(1000);
			second++;
			logger.debug("已等待【{}】秒！", second);
		}
		
		//step3. 返回client
		if(task.isReady()){
			logger.debug("共等待【{}】秒，初始化完成了，Docker版本是【{}】！", second, task.getDockerVersion());
			return task.getDockerClient();
		}
		
		ExceptionUtils.wrapBiz("初始化Docker客户端失败！");
		return null;
	}
	
	
	

	
	
	
	

}
