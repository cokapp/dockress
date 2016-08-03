package com.cokapp.dockress.web.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.dockerjava.api.model.Container;

@Controller
@RequestMapping(value = { "/docker/containers" })
public class ContainersApi extends BaseDockerApi{

	@RequestMapping(value = { "/json" }, method = { RequestMethod.GET })
	@ResponseBody
	public List<Container> json() {
		List<Container> lists = dockerClient.listContainersCmd().exec();
		return lists;
	}

}
