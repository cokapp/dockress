package com.cokapp.quick.module.oauth.core.oauthservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cokapp.cokits.core.lang.exception.ExceptionUtils;
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
public class QQOAuthService extends OAuthServiceDecorator {
	private static final Pattern OPENID_PATTERN = Pattern.compile(".*\"openid\":\"([^\"]*)\".*\n");
	private static final String UID_URL = "https://graph.qq.com/oauth2.0/me";

	private static final String UINFO_URL = "https://graph.qq.com/user/get_user_info";

	public QQOAuthService(OAuthService oAuthService) {
		super(oAuthService, OAuthTypeEnum.QQ);
	}

	@Override
	public User autoBuild(OAuthUser oAuthUser, Token accessToken) {

		OAuthRequest req = new OAuthRequest(Verb.GET, QQOAuthService.UINFO_URL, this.getOAuthService());
		req.addQuerystringParameter("openid", oAuthUser.getOAuthId());
		req.addQuerystringParameter("oauth_consumer_key", this.getOAuthService().getConfig().getApiKey());
		this.getOAuthService().signRequest(accessToken, req);
		Response rsp = req.send();
		ObjectNode rst = JsonMapper.getInstance().fromJson(rsp.getBody(), ObjectNode.class);

		User user = new User();
		user.setUsername(this.getOAuthType().getName() + oAuthUser.getOAuthId());
		user.setAvatar(rst.get("figureurl_qq_1").asText());
		user.setNickname(rst.get("nickname").asText());

		return user;
	}

	@Override
	public OAuthUser readOAuthInfo(Token accessToken) {
		OAuthRequest req = new OAuthRequest(Verb.GET, QQOAuthService.UID_URL, this.getOAuthService());
		this.getOAuthService().signRequest(accessToken, req);
		Response rsp = req.send();
		Matcher matcher = QQOAuthService.OPENID_PATTERN.matcher(rsp.getBody());
		if (!matcher.matches()) {
			ExceptionUtils.wrapBiz("获取OPENID失败！");
		}
		String uid = matcher.group(1);
		OAuthUser oAuthUser = new OAuthUser();
		oAuthUser.setOAuthType(this.getOAuthType());
		oAuthUser.setOAuthId(uid);
		return oAuthUser;
	}

}