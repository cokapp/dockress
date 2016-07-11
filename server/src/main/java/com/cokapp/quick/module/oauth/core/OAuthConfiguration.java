package com.cokapp.quick.module.oauth.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cokapp.quick.core.web.utils.SpringContextUtils;
import com.cokapp.quick.module.oauth.core.api.QQApi20;
import com.cokapp.quick.module.oauth.core.api.WeiboApi20;
import com.cokapp.quick.module.oauth.core.oauthservice.QQOAuthService;
import com.cokapp.quick.module.oauth.core.oauthservice.WeiboOAuthService;
import com.github.scribejava.core.builder.ServiceBuilder;

@Configuration
public class OAuthConfiguration {
	private static final String CALLBACK_TPL = "http://%s/oauth/%s/callback";

	@Bean
	public OAuthServiceDecorator getQQOAuthService() {
		String appKey = SpringContextUtils.getProperty("${oAuth.qq.appKey}");
		String appSecret = SpringContextUtils.getProperty("${oAuth.qq.appSecret}");
		String svrUrl = SpringContextUtils.getProperty("${oAuth.svrUrl}");

		String callback_url = String.format(OAuthConfiguration.CALLBACK_TPL, svrUrl, OAuthTypeEnum.QQ.getName());
		return new QQOAuthService(new ServiceBuilder().provider(QQApi20.class).apiKey(appKey).apiSecret(appSecret)
				.callback(callback_url).build());
	}

	@Bean
	public OAuthServiceDecorator getWeiboOAuthService() {
		String appKey = SpringContextUtils.getProperty("${oAuth.weibo.appKey}");
		String appSecret = SpringContextUtils.getProperty("${oAuth.weibo.appSecret}");
		String svrUrl = SpringContextUtils.getProperty("${oAuth.svrUrl}");

		String callback_url = String.format(OAuthConfiguration.CALLBACK_TPL, svrUrl, OAuthTypeEnum.WEIBO.getName());
		return new WeiboOAuthService(new ServiceBuilder().provider(WeiboApi20.class).apiKey(appKey).apiSecret(appSecret)
				.callback(callback_url).build());
	}

}