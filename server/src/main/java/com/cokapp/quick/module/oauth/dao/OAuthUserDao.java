/*******************************************************************************
 * Dao，重新生成不会覆盖
 *
 * Dao For OAuthUser
 * Auto Generated by CokGen At 2016-01-21 10:40:47
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/
package com.cokapp.quick.module.oauth.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.cokapp.quick.module.oauth.core.OAuthTypeEnum;
import com.cokapp.quick.module.oauth.dao.extend.OAuthUserDaoExtend;
import com.cokapp.quick.module.oauth.entity.OAuthUser;
import com.cokapp.quick.module.oauth_gen.dao.OAuthUserDaoGen;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年8月27日 下午3:32:10
 */
@Repository
public interface OAuthUserDao extends OAuthUserDaoGen, OAuthUserDaoExtend {
	Set<OAuthUser> findByIdIn(Long... ids);

	OAuthUser findByOAuthTypeAndOAuthId(OAuthTypeEnum oAuthType, String oAuthId);
}