/**
 *
 */
package com.cokapp.tongbutie.weixin;

import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.google.common.collect.Lists;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月10日 下午4:19:39
 */
public abstract class MenuUtil {

	public static final String BTN_BINDUSER_KEY = "BindUser";
	public static final String BTN_FETCHLATEST_KEY = "FetchLatest";
	public static final String BTN_SENDPHOTO_KEY = "SendPhoto";

	public static Menu build() {
		MenuButton btnBindUser = MenuUtil.newButton(MenuUtil.BTN_BINDUSER_KEY, "绑定账号", MenuType.VIEW,
				"http://baidu.com");
		MenuButton btnFetchLatest = MenuUtil.newButton(MenuUtil.BTN_FETCHLATEST_KEY, "获取最新", MenuType.CLICK, null);
		MenuButton btnSendPhoto = MenuUtil.newButton(MenuUtil.BTN_SENDPHOTO_KEY, "拍照", MenuType.PIC_SYSPHOTO, null);

		Menu menu = new Menu();
		menu.setButton(Lists.newArrayList(btnBindUser, btnFetchLatest, btnSendPhoto));
		return menu;
	}

	private static MenuButton newButton(String key, String name, MenuType type, String url) {
		MenuButton btn = new MenuButton();
		btn.setKey(key);
		btn.setName(name);
		btn.setType(type);
		btn.setUrl(url);
		return btn;
	}

}
