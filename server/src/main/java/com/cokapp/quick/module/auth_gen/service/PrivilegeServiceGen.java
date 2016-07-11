/*******************************************************************************
 * Service，重新生成会覆盖，不要修改此类
 * 
 * Service For Privilege
 * Auto Generated by CokGen At 2016-03-14 17:37:26
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/
package com.cokapp.quick.module.auth_gen.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cokapp.quick.module.auth.dao.PrivilegeDao;
import com.cokapp.quick.module.auth.entity.Privilege;
import com.cokapp.quick.core.service.BaseService;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年8月31日 上午11:53:23
 */
public abstract class PrivilegeServiceGen extends BaseService<Privilege, Long> {

	private PrivilegeDao dao;

	public PrivilegeDao getDao() {
		return this.dao;
	}

	@Autowired
	protected void setPrivilegeDao(PrivilegeDao dao) {
		this.baseDao = dao;
		this.dao = dao;
	}
}





