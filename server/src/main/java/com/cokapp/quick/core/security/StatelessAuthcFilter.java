package com.cokapp.quick.core.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.cokapp.quick.core.web.view.JsonResult;

public class StatelessAuthcFilter extends AccessControlFilter {
	private static final String TOKEN_KEY = "access_token";

	private StatelessToken decodeToken(String access_token) {
		AuthInfo obj = AuthInfo.deSerialize(access_token);
		StatelessToken token = new StatelessToken();
		token.setAuthInfo(obj);
		return token;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		try {
			String access_token = request.getParameter(StatelessAuthcFilter.TOKEN_KEY);
			StatelessToken token = this.decodeToken(access_token);
			// 委托给Realm进行登录
			this.getSubject(request, response).login(token);
		} catch (Exception e) {
			this.onLoginFail(response);
			return false;
		}
		return true;
	}

	// 登录失败时默认返回401状态码
	private void onLoginFail(ServletResponse response) throws IOException {
		JsonResult<String> json = JsonResult.newError();
		json.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
		json.setMessage("令牌有误！");
		json.ajax(response);
	}
}