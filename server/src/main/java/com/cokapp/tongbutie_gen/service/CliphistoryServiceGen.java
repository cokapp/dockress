/*******************************************************************************
 * Service，重新生成会覆盖，不要修改此类
 * 
 * Service For Cliphistory
 * Auto Generated by CokGen At 2016-01-19 16:20:18
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/
package com.cokapp.tongbutie_gen.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cokapp.tongbutie.dao.CliphistoryDao;
import com.cokapp.tongbutie.entity.Cliphistory;
import com.cokapp.quick.core.service.BaseService;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年8月31日 上午11:53:23
 */
public abstract class CliphistoryServiceGen extends BaseService<Cliphistory, Long> {

	private CliphistoryDao dao;

	public CliphistoryDao getDao() {
		return this.dao;
	}

	@Autowired
	protected void setCliphistoryDao(CliphistoryDao dao) {
		this.baseDao = dao;
		this.dao = dao;
	}
}






