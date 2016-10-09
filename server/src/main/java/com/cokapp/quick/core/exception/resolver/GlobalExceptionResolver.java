package com.cokapp.quick.core.exception.resolver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.cokapp.quick.core.web.view.JsonResult;

/**
 * 扩展的全局异常处理器
 * 
 * @author dev@cokapp.com
 * @date 2015年10月13日 上午11:46:46
 */
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {
	public static Logger logger = LoggerFactory
			.getLogger(GlobalExceptionResolver.class);

	private List<HandlerExceptionResolver> exceptionHandlers;

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		GlobalExceptionResolver.logger.error("发生异常，堆栈信息：{}", ex);

		ModelAndView mv = null;
		// 顺序处理，返回非null时认为处理完毕，忽略后续resolver
		for (HandlerExceptionResolver r : this.exceptionHandlers) {
			mv = r.resolveException(request, response, handler, ex);
			if (mv != null) {
				break;
			}
		}

		// 自定义resolver未处理完成，转基类处理
		if (mv == null) {
			mv = super.doResolveException(request, response, handler, ex);
		}

		boolean ajax = "XMLHttpRequest".equals(request
				.getHeader("X-Requested-With"))
				|| (request.getContentType() != null && request
						.getContentType().toLowerCase()
						.indexOf("application/json") > -1);

		if (!ajax) {
			return mv;
		} else {
			// JSON格式返回
			response.setStatus(HttpStatus.OK.value());

			Object msg = mv.getModel().get("exception");
			if (msg == null) {
				msg = mv.getModel().get("message");
			}
			if (msg == null) {
				msg = mv;
			}

			JsonResult<String> json = JsonResult.newError(msg.toString());
			json.ajax(response);

			return new ModelAndView();
		}
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	public void setExceptionHandlers(
			List<HandlerExceptionResolver> exceptionHandlers) {
		this.exceptionHandlers = exceptionHandlers;
	}
}
