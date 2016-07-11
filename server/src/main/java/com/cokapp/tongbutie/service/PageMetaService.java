package com.cokapp.tongbutie.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import com.cokapp.quick.core.web.meta.IPageMetaService;
import com.cokapp.quick.core.web.meta.annotation.PageMeta;
import com.cokapp.quick.core.web.meta.entity.PageMetaVO;
import com.cokapp.quick.module.auth.service.UserService;

@Service
public class PageMetaService implements IPageMetaService {
	@Autowired
	private UserService userService;

	@Override
	public PageMetaVO buildPageMeta(HttpServletRequest request, HandlerMethod hm) {
		PageMetaVO pageMeta = new PageMetaVO();

		this.setUser(pageMeta, request, hm);
		this.setTitle(pageMeta, request, hm);
		this.setDescription(pageMeta, request, hm);

		return pageMeta;
	}

	private void setDescription(PageMetaVO pageMeta,
			HttpServletRequest request, HandlerMethod hm) {
		PageMeta annotation = hm.getMethodAnnotation(PageMeta.class);
		if (annotation == null) {
			return;
		}
		pageMeta.setDescription(annotation.description());
	}

	private void setTitle(PageMetaVO pageMeta, HttpServletRequest request,
			HandlerMethod hm) {
		PageMeta annotation = hm.getMethodAnnotation(PageMeta.class);
		if (annotation == null) {
			return;
		}
		pageMeta.setTitle(annotation.title());
	}

	private void setUser(PageMetaVO pageMeta, HttpServletRequest request,
			HandlerMethod hm) {

		// SimpleUser user = new SimpleUser();

		// TODO 读取当前用户
	}

}
