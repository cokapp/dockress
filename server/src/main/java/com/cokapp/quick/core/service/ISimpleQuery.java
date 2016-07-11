/**
 *
 */
package com.cokapp.quick.core.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cokapp.quick.core.entity.BaseEntity;
import com.cokapp.quick.core.orm.query.QueryObject;
import com.cokapp.quick.core.web.PageAdapter;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年9月1日 下午5:38:18
 */
public interface ISimpleQuery<T extends BaseEntity<ID>, ID extends Serializable> {
	long count(QueryObject<T> para);

	long countAll();

	boolean exists(ID id);

	List<T> findList();

	List<T> findList(QueryObject<T> para);

	List<T> findList(Sort sort);

	List<T> findList(final String property, final Object value);

	T findOne(ID id);

	T findOne(ID id, String... initLazyPropertyNames);

	T findOne(final String property, final Object value);

	PageAdapter<T> findPage(Pageable pageable);

	PageAdapter<T> findPage(QueryObject<T> para, String... initLazyPropertyNames);
}
