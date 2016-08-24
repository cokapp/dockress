package com.cokapp.quick.core.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cokapp.cokits.core.lang.exception.BaseBizException;

/**
 * 业务异常处理器
 *
 * @author dev@cokapp.com
 * @date 2015年10月13日 上午11:47:52
 */
public class BizExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		// 只处理业务异常
		if (!(ex instanceof BaseBizException)) {
			return null;
		}

		BaseBizException biz = (BaseBizException) ex;

		ModelAndView mv = new ModelAndView();
		mv.setViewName("errors/biz");
		String msg = String.format("发生错误：%s", biz.getMessage());
		mv.addObject("message", msg);

		return mv;
	}
}
