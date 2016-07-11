/*******************************************************************************
 * Dao，重新生成不会覆盖
 *
 * Dao For Clipboard
 * Auto Generated by CokGen At 2015-10-16 10:55:28
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/
package com.cokapp.tongbutie.dao;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cokapp.tongbutie.dao.extend.ClipboardDaoExtend;
import com.cokapp.tongbutie.entity.Clipboard;
import com.cokapp.tongbutie_gen.dao.ClipboardDaoGen;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年8月27日 下午3:32:10
 */
@Repository
public interface ClipboardDao extends ClipboardDaoGen, ClipboardDaoExtend {
	Set<Clipboard> findByIdIn(Long... ids);

	Clipboard findByShortid(String shortid);

	@Modifying
	@Query("update Clipboard c set c.content=:content, c.modifiedBy=:modifiedBy, c.modifiedDate=:modifiedDate where c.id=:id")
	void paste(@Param("id") long id, @Param("content") String content, @Param("modifiedBy") String modifiedBy,
			@Param("modifiedDate") Date modifiedDate);
}