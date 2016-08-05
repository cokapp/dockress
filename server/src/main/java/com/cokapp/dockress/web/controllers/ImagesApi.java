package com.cokapp.dockress.web.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cokapp.quick.core.web.view.JsonResult;
import com.github.dockerjava.api.model.Image;

@Controller
@RequestMapping(value = { "/docker/images" })
public class ImagesApi extends BaseDockerApi {
	
	@RequestMapping(value = { "/json" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<Image> json() {
		List<Image> lists = dockerClient.listImagesCmd().exec();
		
		return JsonResult.newSuccess(lists);
	}
}