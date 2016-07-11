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

import com.cokapp.quick.core.entity.BaseEntity;

/**
 * jpa原生查询接口
 *
 * @author dev@cokapp.com
 * @date 2015年9月1日 下午5:36:00
 */
public interface IQuery<T extends BaseEntity<ID>, ID extends Serializable> extends ISimpleQuery<T, ID> {

	long count(Specification<T> spec);

	List<T> findList(Specification<T> spec);

	List<T> findList(Specification<T> spec, Sort sort);

	Page<T> findPage(Specification<T> spec, Pageable pageable);

}