package com.cokapp.dockress.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cokapp.quick.core.web.view.JsonResult;
import com.github.dockerjava.api.model.Info;

@Controller
@RequestMapping(value = { "/api" })
public class RootApi extends BaseDockerApi {
	public static Logger logger = LoggerFactory.getLogger(RootApi.class);

	@RequestMapping(value = { "/info" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<Info> info() {
		try {
			Info info = getDockerClient().infoCmd().exec();
			return JsonResult.newSuccess(info);
		} catch (Exception e) {
			return JsonResult.newError("获取Docker信息失败，异常：【%s】。", e.getMessage());
		}

	}
}
