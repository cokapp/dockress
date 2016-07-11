/**
 *
 */
package com.cokapp.quick.core.orm.query;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年9月2日 上午10:10:20
 */
public class SimplePageable implements Pageable, Serializable {
	private static final long serialVersionUID = 8280485938848398236L;

	private final int page;
	private final int size;
	private Sort sort;

	public SimplePageable(int page, int size) {
		this(page, size, null);
	}

	public SimplePageable(int page, int size, Sort sort) {

		if (page < 0) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}

		if (size < 0) {
			throw new IllegalArgumentException("Page size must not be less than zero!");
		}

		this.page = page;
		this.size = size;
		this.sort = sort;
	}

	@Override
	public Pageable first() {
		return new SimplePageable(0, this.size, this.sort);
	}

	@Override
	public int getOffset() {
		return this.page * this.size;
	}

	@Override
	public int getPageNumber() {
		return this.page;
	}

	@Override
	public int getPageSize() {

		return this.size;
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}

	@Override
	public boolean hasPrevious() {
		return this.page > 0;
	}

	@Override
	public Pageable next() {
		return new SimplePageable(this.page + 1, this.size, this.sort);
	}

	@Override
	public Pageable previousOrFirst() {
		return this.hasPrevious() ? new SimplePageable(this.page - 1, this.size, this.sort) : this;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return String.format("Page request [number: %d, size %d, sort: %s]", this.page, this.size,
				this.sort == null ? null : this.sort.toString());
	}
}
