package com.cokapp.dockress.dockerjava.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.PortBinding;
import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ContainerCreateVo {

	private String imageId;
	private String name;
	private List<BindVo> binds;
	private List<PortBindingVo> portBindings;
	private List<EnvVo> envs;

	@JsonIgnore
	public List<String> getEnvList() {
		List<String> list = Lists.newArrayList();
		if (envs != null) {
			for (EnvVo env : envs) {
				list.add(env.toEnv());
			}
		}
		return list;
	}

	@JsonIgnore
	public List<Bind> getBindList() {
		List<Bind> list = Lists.newArrayList();
		if (binds != null) {
			for (BindVo bind : binds) {
				list.add(bind.toBind());
			}
		}
		return list;
	}

	@JsonIgnore
	public List<PortBinding> getPortBindingList() {
		List<PortBinding> list = Lists.newArrayList();
		if (portBindings != null) {
			for (PortBindingVo portBinding : portBindings) {
				list.add(portBinding.toPortBinding());
			}
		}
		return list;
	}

}
