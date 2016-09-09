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
	private Boolean readOnly;
	private String hostPath;

	public Bind toBind() {
		Bind bind = new Bind(hostPath, new Volume(path), AccessMode.fromBoolean(!readOnly));
		return bind;
	}
	public Volume toVolume() {
		Volume volume = new Volume(path);
		return volume;
	}
}