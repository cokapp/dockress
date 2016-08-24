package com.cokapp.dockress.dockerjava.api.model;

import com.github.dockerjava.api.model.PortBinding;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PortBindingVo {
	private int port;
	private String protocol;
	private int hostPort;
	
	public PortBinding toPortBinding() {
		// 127.0.0.1:80:8080/tcp
		String serialized = "127.0.0.1:" + port + ":" + hostPort + "/" + protocol;
		PortBinding portBinding = PortBinding.parse(serialized);
		return portBinding;
	}
}