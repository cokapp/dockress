package com.cokapp.dockress.web.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cokapp.dockress.dockerjava.api.model.ContainerCreateVo;
import com.cokapp.quick.core.web.view.JsonResult;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;

@Controller
@RequestMapping(value = { "/docker/containers" })
public class ContainersApi extends BaseDockerApi {

	@RequestMapping(value = { "/json" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<Container> json() {
		List<Container> lists = dockerClient.listContainersCmd().withShowAll(true).exec();

		return JsonResult.newSuccess(lists);
	}

	@RequestMapping(value = { "/{containerId}/start" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult<String> start(@PathVariable(value = "containerId") String containerId) {
		dockerClient.startContainerCmd(containerId).exec();

		return JsonResult.newSuccess("处理成功!");
	}

	@RequestMapping(value = { "/{containerId}/stop" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult<String> stop(@PathVariable(value = "containerId") String containerId) {
		dockerClient.stopContainerCmd(containerId).exec();

		return JsonResult.newSuccess("处理成功!");
	}

	@RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult<CreateContainerResponse> create(@RequestBody ContainerCreateVo containerCreateVo) {

		CreateContainerCmd cmd = dockerClient.createContainerCmd(containerCreateVo.getImageId());

		cmd.withName(containerCreateVo.getName());
		cmd.withPortBindings(containerCreateVo.getPortBindingList());
		cmd.withVolumes(containerCreateVo.getVolumesList());
		cmd.withBinds(containerCreateVo.getBindList());
		cmd.withEnv(containerCreateVo.getEnvList());

		return JsonResult.newSuccess(cmd.exec());
	}

	@RequestMapping(value = { "/edit/{containerId}" }, method = { RequestMethod.POST })
	@ResponseBody
	public void edit(@PathVariable(value = "containerId") String containerId, ContainerCreateVo containerCreateVo) {

		// UpdateContainerCmd cmd =
		// dockerClient.updateContainerCmd(containerId);

	}

	@RequestMapping(value = { "/{containerId}/json" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<InspectContainerResponse> inspect(@PathVariable(value = "containerId") String containerId) {
		InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec();

		return JsonResult.newSuccess(inspectContainerResponse);
	}
}
