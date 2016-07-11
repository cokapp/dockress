/**
 *
 */
package com.cokapp.quick.core.security;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cokapp.quick.module.auth.entity.Privilege;
import com.cokapp.quick.module.auth.entity.Role;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth.service.PrivilegeService;
import com.cokapp.quick.module.auth.service.UserService;

import lombok.Setter;

/**
 * 无状态认证
 *
 * @author dev@cokapp.com
 * @date 2015年10月19日 下午5:17:08
 */
public class StatelessRealm extends AuthorizingRealm {

	@Setter
	private PrivilegeService privilegeService;
	@Setter
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		StatelessToken token = (StatelessToken) authcToken;
		if (StringUtils.isBlank(token.getAuthInfo().getGuid())) {
			throw new AuthenticationException("guid标识参数不能为空!");
		}

		return new SimpleAuthenticationInfo(token.getAuthInfo(), token.getCredentials(), "StatelessRealm");

	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		AuthInfo authInfo = (AuthInfo) principals.getPrimaryPrincipal();
		User user = this.userService.findByGuid(authInfo.getGuid());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		Set<Role> userRoles = user.getRoles();
		for (Role role : userRoles) {
			info.addRole(role.getCode());
		}

		// 基于当前用户所有角色集合获取有效的权限集合
		List<Privilege> privileges = this.privilegeService.findByUserId(user.getId());
		for (Privilege privilege : privileges) {
			info.addStringPermission(privilege.getCode());
		}

		return info;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof StatelessToken;
	}

}