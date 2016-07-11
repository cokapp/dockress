/**
 *
 */
package com.cokapp.quick.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.cokapp.quick.core.entity.BaseEntity;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年8月31日 下午3:02:19
 */

public abstract class QueryService<T extends BaseEntity<ID>, ID extends Serializable> extends SimpleQueryService<T, ID>
		implements IQuery<T, ID> {

	@Override
	@Transactional(readOnly = true)
	public long count(Specification<T> spec) {
		return this.baseDao.count(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Specification<T> spec) {
		return this.baseDao.findAll(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Specification<T> spec, Sort sort) {
		return this.baseDao.findAll(spec, sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findPage(Specification<T> spec, Pageable pageable) {
		return this.baseDao.findAll(spec, pageable);
	}

}
