package com.cokapp.dockress.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dockerjava.api.model.Info;

@Controller
@RequestMapping(value = { "/docker" })
public class RootApi extends BaseDockerApi {

	@RequestMapping(value = { "/info" }, method = { RequestMethod.GET })
	@ResponseBody
	public Info info() {
		Info info = dockerClient.infoCmd().exec();
		return info;
	}
}
