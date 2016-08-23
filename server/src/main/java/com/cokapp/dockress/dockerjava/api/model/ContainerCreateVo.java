package com.cokapp.dockress.dockerjava.api.model;

import java.util.List;

import com.cokapp.cokits.core.lang.util.ArrayUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

	private String name;
	private List<Bind> binds;
	private List<PortBinding> portBindings;
	private List<ObjectNode> envs;

	public List<String> getEnvList() {
		if (envs == null) {
			return null;
		}

		List<String> list = Lists.newArrayList();
		for (ObjectNode env : envs) {
			list.add(ArrayUtils.join(new Object[] { env.get("name"), env.get("value") }, " "));
		}
		
		return list;
	}

}
