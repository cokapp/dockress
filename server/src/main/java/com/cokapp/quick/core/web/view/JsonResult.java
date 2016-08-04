/**
 *
 */
package com.cokapp.quick.core.web.view;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.cokapp.cokits.core.entity.query.ISimplePage;
import com.cokapp.cokits.core.util.LogUtils;
import com.cokapp.cokits.util.entity.EntityTreeBuilder.EntityTreeNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 此类实现有点别扭，是为了使用自定义的@JsonSerialize来实现@ResponseBody的自动属性过滤
 * 如果不继承BaseJsonResult会导致循环引用
 *
 * @see com.cokapp.quick.core.web.view.JsonResultSerializer.toJson(JsonResult
 *      <?>)
 * @param <M>
 * @author dev@cokapp.com
 * @date 2015年10月14日 下午5:49:16
 */
@JsonSerialize(using = JsonResultSerializer.class)
public class JsonResult<M> extends BaseJsonResult<M> {

	public static <M> JsonResult<M> newError() {
		JsonResult<M> json = new JsonResult<M>();
		json.setSuccess(false);
		json.setStatusCode(500);
		return json;
	}

	public static <M> JsonResult<M> newError(String format, Object... args) {
		JsonResult<M> json = JsonResult.newError();
		json.setMessage(format, args);
		return json;
	}

	public static <M> JsonResult<M> newSuccess() {
		JsonResult<M> json = new JsonResult<M>();
		return json;
	}

	public static <M> JsonResult<M> newSuccess(Collection<M> data) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(Collection<M> data, String format, Object... args) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		json.setMessage(format, args);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(EntityTreeNode<M> data) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(EntityTreeNode<M> data, String format, Object... args) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		json.setMessage(format, args);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(ISimplePage<M> data) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(ISimplePage<M> data, String format, Object... args) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		json.setMessage(format, args);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(M data) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(M data, String format, Object... args) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setData(data);
		json.setMessage(format, args);
		return json;
	}

	public static <M> JsonResult<M> newSuccess(String format, Object... args) {
		JsonResult<M> json = JsonResult.newSuccess();
		json.setMessage(format, args);
		return json;
	}

	@JsonIgnore
	public JsonResult<M> addExtraData(String key, Object val) {
		if (this.extraData == null) {
			this.extraData = Maps.newHashMap();
		}
		this.extraData.put(key, val);
		return this;
	}

	@JsonIgnore
	public JsonResult<M> addFilter(Class<?> target, Collection<String> properties) {
		if (this.getJsonFilterMap().containsKey(target)) {
			this.getJsonFilterMap().get(target).addAll(properties);
		} else {
			this.getJsonFilterMap().put(target, properties);
		}
		return this;
	}

	@JsonIgnore
	public JsonResult<M> addFilter(Class<?> target, String... properties) {
		this.addFilter(target, Lists.newArrayList(properties));
		return this;
	}

	@JsonIgnore
	public void ajax(ServletResponse response) {
		try {
			if (response instanceof HttpServletResponse) {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.setStatus(this.status.getCode());
			}
			String jsonStr = this.toJson();
			if (!Strings.isNullOrEmpty(this.cbkey)) {
				// 包装到callback
				StringBuffer buf = new StringBuffer();
				buf.append(this.cbkey);
				buf.append("(");
				buf.append(jsonStr);
				buf.append(");");
				jsonStr = buf.toString();
				response.setContentType("application/javascript;charset=utf-8");
			} else {
				response.setContentType("application/json;charset=utf-8");
			}
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			LogUtils.error(e, "JSON写入错误！");
		}
	}

	@JsonIgnore
	public BaseJsonResult<M> getJsonObject() {
		BaseJsonResult<M> p = new BaseJsonResult<M>();
		p.data = this.data;
		p.extraData = this.extraData;
		p.pageInfo = this.pageInfo;
		p.status = this.status;
		return p;
	}

	public JsonResult<M> setCbkey(String cbkey) {
		this.cbkey = cbkey;
		return this;
	}

	public JsonResult<M> setData(Collection<M> data) {
		this.data = data;
		return this;
	}

	public JsonResult<M> setData(EntityTreeNode<M> data) {
		this.data = data;
		return this;
	}

	@JsonIgnore
	public JsonResult<M> setData(ISimplePage<M> page) {
		this.setData(page.getContent());
		this.pageInfo = PageInfo.instance(page);
		return this;
	}

	public JsonResult<M> setData(M data) {
		this.data = data;
		return this;
	}

	@JsonIgnore
	public JsonResult<M> setMessage(String format, Object... args) {
		this.status.setMessage(String.format(format, args));
		return this;
	}

	@JsonIgnore
	public JsonResult<M> setStatusCode(int statusCode) {
		this.status.setCode(statusCode);
		return this;
	}

	@JsonIgnore
	public JsonResult<M> setSuccess(boolean success) {
		this.status.setSuccess(success);
		return this;
	}

	@Override
	@JsonIgnore
	public String toJson() {
		return super.toJson();
	}

}
