/**
 *
 */
package com.cokapp.tongbutie.web.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.tongbutie.entity.Clipboard;
import com.cokapp.tongbutie.weixin.MenuUtil;
import com.cokapp.tongbutie.weixin.WeixinService;
import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.response.GetMenuResponse;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.QrCodeEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年9月28日 上午11:56:35
 */
@RestController
@RequestMapping("/wx")
public class WeixinController extends WeixinControllerSupport implements InitializingBean {
	private static final String TOKEN = "tongbutie";

	@Autowired
	private MenuAPI menuApi;
	@Autowired
	private WeixinService weixinService;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.ensureMenu();
	}

	private void ensureMenu() {
		GetMenuResponse rsp = this.menuApi.getMenu();
		if (rsp.getMenu() != null) {
			this.menuApi.deleteMenu();
		}

		this.menuApi.createMenu(MenuUtil.build());
	}

	@Override
	protected String getToken() {
		return WeixinController.TOKEN;
	}

	@Override
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		if (MenuUtil.BTN_FETCHLATEST_KEY.equals(event.getEventKey())) {
			Clipboard clipboard = this.weixinService.getDefault(event.getFromUserName());
			return new TextMsg(clipboard.getContent());
		}

		return new TextMsg(String.format("不能处理【%s】事件！", event.getEventKey()));
	}

	@Override
	protected BaseMsg handleQrCodeEvent(QrCodeEvent event) {

		return new TextMsg(event.getEvent() + "," + event.getEventKey() + "," + event.getTicket());
	}

	@Override
	protected BaseMsg handleSubscribe(BaseEvent event) {
		User newUser = this.weixinService.genUser(event.getFromUserName());
		return new TextMsg("系统已经给你创建了一个账号：" + newUser.getUsername() + "，并绑定到此微信号：" + event.getFromUserName());
	}

	@Override
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		String content = msg.getContent();

		Clipboard clipboard = this.weixinService.paste(msg.getFromUserName(), content);
		String rsp = String.format("已粘贴【%s】！", clipboard.getShortid());
		return new TextMsg(rsp);
	}

}
