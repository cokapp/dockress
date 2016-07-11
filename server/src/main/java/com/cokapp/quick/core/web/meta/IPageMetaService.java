package com.cokapp.quick.core.web.meta;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;

import com.cokapp.quick.core.web.meta.entity.PageMetaVO;

public interface IPageMetaService {
	public PageMetaVO buildPageMeta(HttpServletRequest request, HandlerMethod hm);
}
