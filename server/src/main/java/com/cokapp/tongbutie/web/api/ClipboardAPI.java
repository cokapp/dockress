/*******************************************************************************
 * Service，重新生成不会覆盖
 *
 * API For Clipboard
 * Auto Generated by CokGen At 2015-10-16 10:55:28
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/
package com.cokapp.tongbutie.web.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cokapp.cokits.core.lang.exception.ExceptionUtils;
import com.cokapp.quick.core.web.view.JsonResult;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth.service.UserService;
import com.cokapp.tongbutie.common.Const;
import com.cokapp.tongbutie.entity.Clipboard;
import com.cokapp.tongbutie_gen.web.api.ClipboardAPIGen;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
@RequestMapping(value = { "/api/clipboard" })
public class ClipboardAPI extends ClipboardAPIGen {

	@Autowired
	private UserService userService;

	@Override
	@RequestMapping(value = { "" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult<Clipboard> create(@RequestBody Clipboard entity) {
		entity.setCreatedBy(this.userService.getCurrentUserIdStr());

		this.getService().save(entity);
		return JsonResult.newSuccess(entity, "新建成功！");
	}

	@Override
	@RequestMapping(value = { "" }, method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult<Clipboard> list(HttpServletRequest request,
			@RequestParam(value = "paged", required = false, defaultValue = "false") Boolean paged) {
		JsonResult<Clipboard> json = JsonResult.newSuccess();
		if (paged) {
			json.setData(this.findPage(request));
		} else {
			json.setData(this.findList(request));
		}
		return json;
	}

	/**
	 * 粘贴，最常使用的API，需要注意性能
	 *
	 * @param id
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/{id}/paste", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult<Clipboard> paste(@PathVariable("id") Long id, @RequestBody String content) {
		this.getService().paste(id, content, this.userService.getCurrentUserIdStr(), new Date());

		return JsonResult.<Clipboard> newSuccess("成功完成！");
	}

	@RequestMapping(value = "/s/{shortid}", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult<Clipboard> show(@PathVariable("shortid") String shortid) {
		Clipboard entity = this.getService().findByShortid(shortid.toLowerCase());
		if (entity == null) {
			return JsonResult.<Clipboard> newError("没有找到Shortid(%s)对应的记录！", shortid).setStatusCode(404);
		}
		return JsonResult.newSuccess(entity);
	}

	@RequestMapping(value = "/s", method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public JsonResult<Clipboard> showDefault() {
		User user = this.userService.getCurrentUser();
		JsonNode clipId = user.getExtra(Const.DEFAULT_CLIPBOARD_FILED);
		ExceptionUtils.wrapIfTrue(clipId == null, "用户【%s】未设置默认剪贴板！", user);
		String id = clipId.asText();

		Clipboard entity = this.getService().findOne(Long.valueOf(id));
		if (entity == null) {
			return JsonResult.<Clipboard> newError("没有找到Id(%s)对应的记录！", id).setStatusCode(404);
		}
		return JsonResult.newSuccess(entity);
	}

	@Override
	@RequestMapping(value = { "/{id}" }, method = { RequestMethod.POST })
	@ResponseBody
	public JsonResult<Clipboard> update(@RequestBody Clipboard entity, @PathVariable("id") Long id) {
		entity.setId(id);
		entity.setModifiedBy(this.userService.getCurrentUserIdStr());
		entity.setModifiedDate(new Date());

		this.getService().update(entity);
		return JsonResult.newSuccess(entity);
	}

	@Override
	public JsonResult<Clipboard> view(@PathVariable("id") Long id) {
		return JsonResult.<Clipboard> newError("此API已禁用！").setStatusCode(403);
	}

}