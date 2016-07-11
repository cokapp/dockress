/**
 *
 */
package com.cokapp.quick.core.orm.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import lombok.Getter;

/**
 *
 * 统一查询对象
 *
 * @author dev@cokapp.com
 * @date 2015年9月2日 上午9:32:41
 */
@Getter
public class QueryObject<T> {
	public static <T> QueryObject<T> create(Criteria<T> specification, Pageable pageable) {
		return QueryObject.create(specification, pageable, null);
	}

	public static <T> QueryObject<T> create(Specification<T> specification) {
		return QueryObject.create(specification, null, null);
	}

	public static <T> QueryObject<T> create(Specification<T> specification, Pageable pageable, Sort sortable) {
		QueryObject<T> obj = new QueryObject<T>(specification, pageable, sortable);
		return obj;
	}

	private final Pageable pageable;
	private final Sort sortable;
	private final Specification<T> specification;

	private QueryObject(Specification<T> specification, Pageable pageable, Sort sortable) {
		this.specification = specification;
		this.pageable = pageable;
		this.sortable = sortable;
	}

	public boolean isPageable() {
		return this.pageable != null;
	}

	public boolean isSortable() {
		return this.sortable != null;
	}

}
