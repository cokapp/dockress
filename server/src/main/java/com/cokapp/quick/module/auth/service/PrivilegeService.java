/*******************************************************************************
 * Service，重新生成不会覆盖
 *
 * Service For Privilege
 * Auto Generated by CokGen At 2015-10-16 15:50:47
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/

package com.cokapp.quick.module.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cokapp.quick.module.auth.entity.Privilege;
import com.cokapp.quick.module.auth_gen.service.PrivilegeServiceGen;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年8月31日 上午11:53:13
 */
@Service
@Transactional
public class PrivilegeService extends PrivilegeServiceGen {

	public List<Privilege> findByUserId(Long userid) {
		return this.getDao().findByUserId(userid);
	}

}