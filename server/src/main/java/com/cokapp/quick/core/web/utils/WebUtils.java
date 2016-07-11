/**
 * 
 */
package com.cokapp.quick.core.web.utils;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author dev@cokapp.com
 * @date 2016年1月20日 下午10:52:41
 */
public class WebUtils {

	/**
	 * 获取服务器跟路径
	 * 
	 * @param req
	 * @return
	 */
	public static String getServerURL(ServletRequest req) {
		String tpl = "%s://%s:%s/";

		String url = String.format(tpl, req.getScheme(), req.getServerName(),
				req.getServerPort());

		if (StringUtils.isNotBlank((req.getServletContext().getContextPath()))) {
			url += req.getServletContext().getContextPath() + "/";
		}

		return url;
	}
}
