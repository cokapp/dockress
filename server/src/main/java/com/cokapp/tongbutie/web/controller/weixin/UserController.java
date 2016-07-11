/**
 *
 */
package com.cokapp.tongbutie.web.controller.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cokapp.quick.core.security.PasswordService;
import com.cokapp.quick.core.web.controller.BaseWebController;
import com.cokapp.quick.core.web.meta.annotation.PageMeta;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth.service.UserService;
import com.cokapp.quick.module.oauth.core.OAuthTypeEnum;
import com.cokapp.quick.module.oauth.entity.OAuthUser;
import com.cokapp.quick.module.oauth.service.OAuthUserService;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年11月11日 下午4:03:57
 */
@Controller("wxUserController")
@RequestMapping(value = { "/weixin/user" })
public class UserController extends BaseWebController {
	@Autowired
	private OAuthUserService oAuthUserService;
	@Autowired
	private UserService userService;

	@PageMeta(title = "用户信息设置", description = "设置用户名和密码！")
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	public ModelAndView bind(HttpServletRequest request, HttpServletResponse response, ModelAndView mv,
			@ModelAttribute("user") User user) {

		User dbUser = this.userService.findByName(user.getUsername());
		dbUser.setUsername(user.getUsername());
		dbUser.setNickname(user.getNickname());
		if (!StringUtils.isEmpty(user.getPassword())) {
			String entryptedPassword = PasswordService.entryptPassword(user.getPassword(), dbUser.getGuid());
			dbUser.setPassword(entryptedPassword);
		}
		this.userService.update(dbUser);

		mv.addObject("user", user);
		mv.setViewName(this.viewName("bind"));
		return mv;
	}

	@PageMeta(title = "用户信息设置", description = "设置用户名和密码！")
	@RequestMapping(value = "/bind", method = RequestMethod.GET)
	public ModelAndView bindForm(HttpServletRequest request, HttpServletResponse response, ModelAndView mv,
			@RequestParam(value = "uid") String wxUid) {
		OAuthUser oAuthUser = this.oAuthUserService.findByOAuthTypeAndOAuthId(OAuthTypeEnum.WEIXIN, wxUid);
		mv.addObject("user", oAuthUser.getBindTo());
		mv.setViewName(this.viewName("bind"));
		return mv;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

		mv.setViewName(this.viewName("index"));
		return mv;
	}
}
