package com.cokapp.quick.core.exception.resolver;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处理Sql异常
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年5月7日 上午11:30:45
 */
public class SqlExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		// 只处理Sql异常
		if (!(ex.getCause() instanceof SQLException)) {
			return null;
		}

		SQLException sqex = (SQLException) ex.getCause();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("errors/biz");
		String msg = String.format("SQL执行错误：%s", sqex.getMessage());
		mv.addObject("message", msg);

		return mv;
	}

}
