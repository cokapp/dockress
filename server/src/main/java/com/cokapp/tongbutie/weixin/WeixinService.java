/**
 *
 */
package com.cokapp.tongbutie.weixin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth.service.UserService;
import com.cokapp.quick.module.oauth.core.OAuthTypeEnum;
import com.cokapp.quick.module.oauth.service.OAuthUserService;
import com.cokapp.tongbutie.common.Const;
import com.cokapp.tongbutie.entity.Clipboard;
import com.cokapp.tongbutie.service.ClipboardService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年11月11日 下午3:28:56
 */
@Service
@Transactional
public class WeixinService {
	@Autowired
	private ClipboardService clipboardService;
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private OAuthUserService oAuthUserService;

	@Autowired
	private UserService userService;

	public User genUser(String wxUid) {
		User user = this.userService.genWeixinUser(wxUid);
		if (user.getExtra(Const.DEFAULT_CLIPBOARD_FILED) == null) {
			Clipboard clipboard = new Clipboard();
			clipboard.setName("默认剪贴板");
			clipboard.setContent("默认剪贴板！");
			this.clipboardService.save(clipboard);
			user.putExtra(Const.DEFAULT_CLIPBOARD_FILED, JsonNodeFactory.instance.numberNode(clipboard.getId()));
			this.userService.update(user);
		}

		return user;
	}

	/**
	 * 获取默认剪贴板
	 *
	 * @param wxUid
	 * @return
	 */
	public Clipboard getDefault(String wxUid) {
		User user = this.oAuthUserService.findByOAuthTypeAndOAuthId(OAuthTypeEnum.WEIXIN, wxUid).getBindTo();
		long clipboardid = user.getExtra(Const.DEFAULT_CLIPBOARD_FILED).asLong();
		Clipboard clipboard = this.clipboardService.findOne(clipboardid);
		return clipboard;
	}

	/**
	 * 向默认剪贴板粘贴
	 *
	 * @param wxUid
	 * @param content
	 * @return
	 */
	public Clipboard paste(String wxUid, String content) {
		Clipboard clipboard = this.getDefault(wxUid);
		// 此操作是为了避免this.update无法正确插入历史数据
		this.entityManager.detach(clipboard);
		clipboard.setContent(content);
		return this.clipboardService.update(clipboard);
	}

}
