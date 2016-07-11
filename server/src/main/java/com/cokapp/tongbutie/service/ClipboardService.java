/*******************************************************************************
 * Service，重新生成不会覆盖
 *
 * Service For Clipboard
 * Auto Generated by CokGen At 2015-10-16 10:55:28
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/

package com.cokapp.tongbutie.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokapp.cokits.core.util.Hashids;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth.service.UserService;
import com.cokapp.tongbutie.common.Const;
import com.cokapp.tongbutie.entity.ClipSubscribe;
import com.cokapp.tongbutie.entity.Clipboard;
import com.cokapp.tongbutie.entity.Cliphistory;
import com.cokapp.tongbutie_gen.service.ClipboardServiceGen;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年8月31日 上午11:53:13
 */
@Service
@Transactional
public class ClipboardService extends ClipboardServiceGen {
	@Autowired
	private CliphistoryService cliphistoryService;
	@Autowired
	private ClipSubscribeService clipSubscribeService;
	@Autowired
	private UserService userService;

	public Clipboard findByShortid(String shortid) {
		return this.getDao().findByShortid(shortid);
	}

	public void paste(long id, String content, String modifiedBy, Date modifiedDate) {
		// 1.粘贴
		this.getDao().paste(id, content, modifiedBy, modifiedDate);

		// 2.插入历史表
		Cliphistory cliphistory = new Cliphistory();
		cliphistory.setClipboard(new Clipboard(id));
		cliphistory.setContent(content);
		cliphistory.setCreatedBy(modifiedBy);
		cliphistory.setModifiedBy(modifiedBy);

		this.cliphistoryService.save(cliphistory);
	}

	@Override
	public Clipboard save(Clipboard t) {
		if (t.getShortid() == null) {
			super.save(t);
			String shortid = Hashids.shortid(t.getId()).toLowerCase();
			t.setShortid(shortid);
			return this.update(t);
		} else {
			return super.save(t);
		}
	}

	/**
	 * 订阅剪贴板
	 *
	 * @param userid
	 * @param clipboardid
	 * @param asDefault
	 */
	public void subscribe(long userid, long clipboardid, boolean asDefault) {
		ClipSubscribe clipSubscribe = this.clipSubscribeService.findByClipboardAndUser(clipboardid, userid);

		if (clipSubscribe == null) {
			clipSubscribe = new ClipSubscribe();
			clipSubscribe.setClipboard(clipboardid);
			clipSubscribe.setUser(userid);
			this.clipSubscribeService.save(clipSubscribe);
		}

		if (asDefault) {
			User user = this.userService.findOne(userid);
			user.putExtra(Const.DEFAULT_CLIPBOARD_FILED, JsonNodeFactory.instance.numberNode(clipboardid));
			this.userService.save(user);
		}
	}

	@Override
	public Clipboard update(Clipboard t) {
		String oldContent = this.findOne(t.getId()).getContent();
		super.update(t);

		// 插入历史纪录
		if ((t.getContent() == null && oldContent != null)
				|| (t.getContent() != null && !t.getContent().equals(oldContent))) {
			Cliphistory his = new Cliphistory();
			his.setClipboard(t);
			his.setContent(t.getContent());
			his.setCreatedBy(t.getModifiedBy());
			his.setModifiedBy(t.getModifiedBy());
			this.cliphistoryService.save(his);
		}

		return t;
	}

}