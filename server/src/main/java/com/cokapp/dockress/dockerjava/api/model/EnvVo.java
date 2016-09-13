package com.cokapp.dockress.dockerjava.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EnvVo {
	private String name;
	private String value;

	public String toEnv() {
		return name + "=" + value;
	}
}