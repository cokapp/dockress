package com.cokapp.quick.core.service;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;

import com.cokapp.quick.core.entity.BaseEntity;

@Transactional
public abstract class BaseService<T extends BaseEntity<ID>, ID extends Serializable>
		extends QueryService<T, ID> {

	public void delete(ID id) {
		this.baseDao.delete(id);
	}

	public void delete(T m) {
		this.baseDao.delete(m);
	}

	public T save(T t) {
		return this.baseDao.save(t);
	}

	public T update(T t) {
		if (!this.getEntityManager().contains(t)) {
			T dbEntity = this.baseDao.findOne(t.getId());
			BeanUtils.copyProperties(t, dbEntity, "id", "version");
			t = dbEntity;
		}

		return this.baseDao.save(t);
	}
}