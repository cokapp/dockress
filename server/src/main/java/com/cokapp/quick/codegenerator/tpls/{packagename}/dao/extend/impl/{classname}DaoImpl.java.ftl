/*******************************************************************************
 * DaoExtend实现类，重新生成不会覆盖
 * 
 * Dao For ${meta.getClassName()}
 * Auto Generated by CokGen At ${date.toString("YYYY-MM-dd HH:mm:ss")}
 *
 * Copyright (c) cokapp.com
 * Email: dev@cokapp.com
 *******************************************************************************/

package ${meta.getPackageName()}.dao.extend.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ${meta.getPackageName()}.dao.extend.${meta.getClassName()}DaoExtend;

public class ${meta.getClassName()}DaoImpl implements ${meta.getClassName()}DaoExtend{
	@PersistenceContext
	private EntityManager entityManager;
}