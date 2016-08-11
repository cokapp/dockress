package com.cokapp.dockress.dockerjava.api.model;

import java.util.List;

import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.PortBinding;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ContainerCreateVo {

	private String name;
	private List<Bind> binds; 
	private List<PortBinding> portBindings;
	private List<String> envs; 
	
}
