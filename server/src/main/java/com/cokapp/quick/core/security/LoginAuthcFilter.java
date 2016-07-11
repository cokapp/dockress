/**
 *
 */
package com.cokapp.quick.core.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import lombok.Setter;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.cokapp.quick.core.web.view.JsonResult;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth.service.PrivilegeService;
import com.cokapp.quick.module.auth.service.UserService;

/**
 * 
 * @author dev@cokapp.com
 * @date 2015年10月20日 下午4:41:05
 */
public class LoginAuthcFilter extends AccessControlFilter {
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";

	@Setter
	private PrivilegeService privilegeService;
	@Setter
	private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		String username = request.getParameter(LoginAuthcFilter.USERNAME);
		String password = request.getParameter(LoginAuthcFilter.PASSWORD);

		User user = this.userService.findByName(username);
		if (user == null) {
			this.onLoginFail(response);
			return false;
		}
		String entryptedPassword = PasswordService.entryptPassword(password,
				user.getGuid());
		if (!entryptedPassword.equals(user.getPassword())) {
			this.onLoginFail(response);
			return false;
		}

		AuthInfo info = new AuthInfo();
		info.setGuid(user.getGuid());
		info.setExpireIn(0);
		String token = info.serialize();

		JsonResult<String> json = JsonResult.newSuccess();
		json.addExtraData("access_token", token);
		json.addExtraData("expires_in", info.getExpireIn());
		json.ajax(response);

		return false;
	}

	private void onLoginFail(ServletResponse response) throws IOException {
		JsonResult<String> json = JsonResult.newError();
		json.setStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
		json.setMessage("登录失败！");
		json.ajax(response);
	}
}
