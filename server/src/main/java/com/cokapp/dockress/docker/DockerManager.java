package com.cokapp.dockress.docker;

import com.cokapp.dockress.web.utils.SpringContextUtils;
import com.github.dockerjava.api.DockerClient;

/**
 * Docker客户端管理
 * 
 * @Description: TODO
 * @Copyright: Copyright (c) 2015
 * @version V1.0.0
 * @since JDK 1.7
 * @date 2016年8月3日 下午4:44:16
 *
 */
public class DockerManager {
	public static DockerClient getDefault() {
		return SpringContextUtils.getBean(DockerClient.class);
	}
}
