/**
 *
 */
package com.cokapp.quick.module.oauth.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cokapp.quick.core.security.AuthInfo;
import com.cokapp.quick.core.web.view.JsonResult;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth.service.UserService;
import com.cokapp.quick.module.oauth.core.OAuthServcies;
import com.cokapp.quick.module.oauth.core.OAuthServiceDecorator;
import com.cokapp.quick.module.oauth.core.OAuthTypeEnum;
import com.cokapp.quick.module.oauth.entity.OAuthUser;
import com.cokapp.quick.module.oauth.service.OAuthUserService;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verifier;

/**
 * OAuth认证
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2016年1月21日 下午2:28:40
 */
@Controller
@RequestMapping(value = { "/oauth" })
public class OAuthController {
	private static final Token EMPTY_TOKEN = null;
	public static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

	@Autowired
	private OAuthServcies oAuthServcies;
	@Autowired
	private OAuthUserService oAuthUserService;
	@Autowired
	private UserService userServcie;

	@RequestMapping(value = { "/{type}/callback" }, method = { RequestMethod.GET })
	public ModelAndView callback(@RequestParam(value = "code", required = true) String code,
			@PathVariable(value = "type") String type) {
		OAuthTypeEnum oAuthType = OAuthTypeEnum.byName(type);
		OAuthServiceDecorator oAuthService = this.oAuthServcies.getOAuthService(oAuthType);
		Token accessToken = oAuthService.getOAuthService().getAccessToken(OAuthController.EMPTY_TOKEN,
				new Verifier(code));
		OAuthUser oAuthInfo = oAuthService.readOAuthInfo(accessToken);
		OAuthUser oAuthUser = this.oAuthUserService.findByOAuthTypeAndOAuthId(oAuthInfo.getOAuthType(),
				oAuthInfo.getOAuthId());

		JsonResult<User> json = JsonResult.newSuccess();
		json.setMessage("授权成功！");

		// 未注册
		if (oAuthUser == null) {
			// 1.自动注册
			User user = oAuthService.autoBuild(oAuthInfo, accessToken);
			this.userServcie.autoRegister(oAuthInfo, user);

			oAuthUser = oAuthInfo;
		}

		// 2.生成access_token
		String access_token = AuthInfo.gen(oAuthUser.getBindTo().getGuid()).serialize();

		// 3.返回前台
		ModelAndView mv = new ModelAndView("/oauth/success");
		mv.addObject("access_token", access_token);
		// mv.addObject("user", oAuthUser.getBindTo());
		// mv.addObject("needupdate", !oAuthUser.getBindTo().isNameupdated());

		return mv;
	}

	@RequestMapping(value = { "/{type}" }, method = { RequestMethod.GET })
	public String index(@PathVariable(value = "type") String type) {
		OAuthTypeEnum oAuthType = OAuthTypeEnum.byName(type);
		OAuthServiceDecorator oAuthService = this.oAuthServcies.getOAuthService(oAuthType);

		String authUrl = oAuthService.getOAuthService().getAuthorizationUrl(null);
		return "redirect:" + authUrl;
	}

}
