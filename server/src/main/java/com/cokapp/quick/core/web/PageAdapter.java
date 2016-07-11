/**
 *
 */
package com.cokapp.quick.core.web;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;

import com.cokapp.cokits.core.entity.query.ISimplePage;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年9月2日 下午4:02:58
 */
public class PageAdapter<T> implements ISimplePage<T> {

	public static <T> PageAdapter<T> adapte(Page<T> page) {
		return new PageAdapter<T>(page);
	}

	private final Page<T> page;

	private PageAdapter(Page<T> page) {
		this.page = page;
	}

	@Override
	public List<T> getContent() {
		return this.page.getContent();
	}

	@Override
	public int getNumber() {
		return this.page.getNumber();
	}

	@Override
	public int getNumberOfElements() {
		return this.page.getNumberOfElements();
	}

	public Page<T> getPage() {
		return this.page;
	}

	@Override
	public int getSize() {
		return this.page.getSize();
	}

	@Override
	public long getTotalElements() {
		return this.page.getTotalElements();
	}

	@Override
	public int getTotalPages() {
		return this.page.getTotalPages();
	}

	@Override
	public boolean hasContent() {
		return this.page.hasContent();
	}

	@Override
	public boolean hasNextPage() {
		return this.page.hasNext();
	}

	@Override
	public boolean hasPreviousPage() {
		return this.page.hasPrevious();
	}

	@Override
	public boolean isFirstPage() {
		return this.page.isFirst();
	}

	@Override
	public boolean isLastPage() {
		return this.page.isLast();
	}

	@Override
	public Iterator<T> iterator() {
		return this.page.iterator();
	}

}
