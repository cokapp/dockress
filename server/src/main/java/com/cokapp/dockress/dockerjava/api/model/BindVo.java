package com.cokapp.dockress.dockerjava.api.model;

import com.github.dockerjava.api.model.AccessMode;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BindVo {
	private String path;
	private AccessMode accessMode;
	private String hostPath;

	public Bind toBind() {
		Bind bind = new Bind(hostPath, new Volume(path), accessMode);
		return bind;
	}
}