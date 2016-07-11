package com.cokapp.quick.module.oauth.core.oauthservice;

import com.cokapp.cokits.util.mapper.JsonMapper;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.oauth.core.OAuthServiceDecorator;
import com.cokapp.quick.module.oauth.core.OAuthTypeEnum;
import com.cokapp.quick.module.oauth.entity.OAuthUser;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuthService;

/**
 * 微博OAuth认证服务
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2016年1月21日 上午11:26:28
 */
public class WeiboOAuthService extends OAuthServiceDecorator {
	private static final String UID_URL = "https://api.weibo.com/oauth2/get_token_info";
	private static final String UINFO_URL = "https://api.weibo.com/2/users/show.json";

	public WeiboOAuthService(OAuthService oAuthService) {
		super(oAuthService, OAuthTypeEnum.WEIBO);
	}

	@Override
	public User autoBuild(OAuthUser oAuthUser, Token accessToken) {

		OAuthRequest req = new OAuthRequest(Verb.GET, WeiboOAuthService.UINFO_URL, this.getOAuthService());
		req.addQuerystringParameter("uid", oAuthUser.getOAuthId());
		this.getOAuthService().signRequest(accessToken, req);
		Response rsp = req.send();
		ObjectNode rst = JsonMapper.getInstance().fromJson(rsp.getBody(), ObjectNode.class);

		User user = new User();
		user.setUsername(this.getOAuthType().getName() + oAuthUser.getOAuthId());
		user.setAvatar(rst.get("profile_image_url").asText());
		user.setNickname(rst.get("screen_name").asText());

		return user;
	}

	@Override
	public OAuthUser readOAuthInfo(Token accessToken) {
		OAuthRequest req = new OAuthRequest(Verb.POST, WeiboOAuthService.UID_URL, this.getOAuthService());
		this.getOAuthService().signRequest(accessToken, req);
		Response rsp = req.send();
		ObjectNode rst = JsonMapper.getInstance().fromJson(rsp.getBody(), ObjectNode.class);
		String uid = rst.get("uid").asText();

		OAuthUser oAuthUser = new OAuthUser();
		oAuthUser.setOAuthType(this.getOAuthType());
		oAuthUser.setOAuthId(uid);
		return oAuthUser;
	}

}