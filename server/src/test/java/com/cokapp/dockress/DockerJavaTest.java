package com.cokapp.dockress;

import org.junit.Test;

import com.github.dockerjava.api.model.PortBinding;

public class DockerJavaTest {
	
	
	@Test
	public void portBindingTest() {
		
		PortBinding pb = PortBinding.parse("127.0.0.1:80:8080/tcp");
		
	System.out.println(	pb.getBinding().getHostIp());
	System.out.println(pb.getBinding().getHostPortSpec());
		
	System.out.println(pb.getExposedPort().getPort());
	System.out.println(pb.getExposedPort().getProtocol());
		
		
		
	}
}
