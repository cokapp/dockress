/*******************************************************************************
 * Dao，重新生成不会覆盖
 *
 * Dao For User
 * Auto Generated by CokGen At 2015-10-16 15:50:47
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/
package com.cokapp.quick.module.auth.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cokapp.quick.module.auth.dao.extend.UserDaoExtend;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.auth_gen.dao.UserDaoGen;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年8月27日 下午3:32:10
 */
@Repository
public interface UserDao extends UserDaoGen, UserDaoExtend {
	User findByGuid(String guid);

	Set<User> findByIdIn(Long... ids);

	@Query("select u from User u where u.username=:name or u.email=:name")
	User findByName(@Param("name") String name);
}
