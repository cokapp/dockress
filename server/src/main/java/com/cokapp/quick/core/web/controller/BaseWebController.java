/**
 *
 */
package com.cokapp.quick.core.web.controller;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cokapp.quick.core.web.meta.annotation.PageMeta;

/**
 * 
 * @author heichengliang@talkweb.com.cn
 * @date 2015年11月11日 下午4:25:29
 */
@PageMeta
public abstract class BaseWebController {
	private String viewPrefix;

	protected BaseWebController() {
		this.setViewPrefix(this.getDefaultViewPrefix());
	}

	private String getDefaultViewPrefix() {
		String currentViewPrefix = "";
		RequestMapping requestMapping = AnnotationUtils.findAnnotation(
				this.getClass(), RequestMapping.class);
		if (requestMapping != null && requestMapping.value().length > 0) {
			currentViewPrefix = requestMapping.value()[0];
		}

		if (StringUtils.isEmpty(currentViewPrefix)) {
			currentViewPrefix = this.getClass().getSimpleName();
		}

		return currentViewPrefix;
	}

	private void setViewPrefix(String viewPrefix) {
		if (viewPrefix.startsWith("/")) {
			viewPrefix = viewPrefix.substring(1);
		}
		this.viewPrefix = viewPrefix;
	}

	public String viewName(String suffixName) {
		String prefix = this.viewPrefix;
		if (!suffixName.startsWith("/") && !prefix.endsWith("/")) {
			suffixName = "/" + suffixName;
		}
		if (suffixName.startsWith("/") && prefix.endsWith("/")) {
			suffixName = suffixName.substring(1);
		}
		return prefix + suffixName;
	}

}
