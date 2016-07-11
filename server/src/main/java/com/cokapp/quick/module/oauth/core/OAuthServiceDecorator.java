package com.cokapp.quick.module.oauth.core;

import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.oauth.entity.OAuthUser;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;

import lombok.Getter;

/**
 * OAuth认证服务，装饰对象
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2016年1月21日 上午10:50:04
 */
@Getter
public abstract class OAuthServiceDecorator {
	private static final Token EMPTY_TOKEN = null;
	private String authorizationUrl;
	private OAuthService oAuthService;
	private OAuthTypeEnum oAuthType;

	public OAuthServiceDecorator(OAuthService oAuthService, OAuthTypeEnum oAuthType) {
		this.oAuthService = oAuthService;
		this.oAuthType = oAuthType;
		this.authorizationUrl = oAuthService.getAuthorizationUrl(OAuthServiceDecorator.EMPTY_TOKEN);
	}

	public abstract User autoBuild(OAuthUser oAuthUser, Token accessToken);

	public abstract OAuthUser readOAuthInfo(Token accessToken);

}