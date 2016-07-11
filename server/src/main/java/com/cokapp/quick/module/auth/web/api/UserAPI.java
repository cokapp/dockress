/*******************************************************************************
 * Service，重新生成不会覆盖
 *
 * API For User
 * Auto Generated by CokGen At 2015-10-16 15:50:47
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/
package com.cokapp.quick.module.auth.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cokapp.quick.core.web.view.JsonResult;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth_gen.web.api.UserAPIGen;

@Controller
@RequestMapping(value = { "/api/user" })
public class UserAPI extends UserAPIGen {

	@Override
	public JsonResult<User> create(@RequestBody User entity) {
		return JsonResult.<User> newError("此API已禁用！").setStatusCode(403);
	}

	@RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult<String> login(@RequestBody User user) {
		// TODO 读取客户端信息
		String token = this.getService().login(user);

		JsonResult<String> json = JsonResult.newSuccess();
		json.addExtraData("access_token", token);
		return json;
	}

	@RequestMapping(value = { "/me" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<User> me() {
		User user = this.getService().getCurrentUser();

		JsonResult<User> json = JsonResult.newSuccess();
		json.setData(user);
		json.setMessage("读取成功！");
		return json;
	}

	@RequestMapping(value = { "/register" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<String> preRegister(@RequestParam("email") String email) {
		this.getService().preRegister(email);
		JsonResult<String> json = JsonResult.newSuccess();
		json.setMessage("邮箱验证码发送成功，请登录邮箱获取！");
		return json;
	}

	@RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult<String> register(@RequestBody User user,
			@RequestParam("randomCode") String randomCode) {
		this.getService().register(user, randomCode);

		JsonResult<String> json = JsonResult.newSuccess();
		json.setMessage("注册成功，请登录！");
		return json;
	}

}
