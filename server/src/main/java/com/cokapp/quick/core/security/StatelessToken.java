package com.cokapp.quick.core.security;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.Getter;
import lombok.Setter;

public class StatelessToken implements AuthenticationToken {
	private static final long serialVersionUID = -978290191485607513L;

	@Setter
	@Getter
	private AuthInfo authInfo;

	@Override
	public Object getCredentials() {
		return this.authInfo;
	}

	@Override
	public Object getPrincipal() {
		return this.authInfo.getGuid();
	}
}