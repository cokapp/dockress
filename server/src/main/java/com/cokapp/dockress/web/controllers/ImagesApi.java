package com.cokapp.dockress.web.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cokapp.quick.core.web.view.JsonResult;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;

@Controller
@RequestMapping(value = { "/api/images" })
public class ImagesApi extends BaseDockerApi {
	
	@RequestMapping(value = { "/json" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<Image> json() {
		List<Image> lists = getDockerClient().listImagesCmd().exec();
		
		return JsonResult.newSuccess(lists);
	}
	
	@RequestMapping(value = { "/{imageId}/json" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<InspectImageResponse> inspect(@PathVariable(value = "imageId") String imageId) {
		InspectImageResponse inspectImageResponse = getDockerClient().inspectImageCmd(imageId).exec();

		return JsonResult.newSuccess(inspectImageResponse);		
	}	
	
	@RequestMapping(value = { "/{imageId}/remove" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<String> remove(@PathVariable(value = "imageId") String imageId) {
		getDockerClient().removeImageCmd(imageId).exec();

		return JsonResult.newSuccess("处理成功!");
	}
	
	
}
