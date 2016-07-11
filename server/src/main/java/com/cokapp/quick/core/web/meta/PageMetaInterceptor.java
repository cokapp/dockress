package com.cokapp.quick.core.web.meta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cokapp.quick.core.web.meta.annotation.PageMeta;
import com.cokapp.quick.core.web.meta.entity.PageMetaVO;

public class PageMetaInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private IPageMetaService pageMetaService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	/**
	 * 根据注解判断是否需要注入页面元数据
	 * 
	 * @param hm
	 * @return
	 */
	private boolean needInject(HandlerMethod hm) {
		PageMeta annotation = hm.getBeanType().getAnnotation(PageMeta.class);
		if (annotation == null) {
			annotation = hm.getMethodAnnotation(PageMeta.class);
		}

		// 类上和方法上都没有注解，不需要注入元数据
		if (annotation == null) {
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mv)
			throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return;
		}
		HandlerMethod hm = (HandlerMethod) handler;
		if (!this.needInject(hm)) {
			return;
		}

		PageMetaVO meta = this.pageMetaService.buildPageMeta(request, hm);
		mv.addObject(PageMetaVO.KEY_IN_MODEL, meta);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

}
