/**
 *
 */
package com.cokapp.quick.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.cokapp.quick.core.entity.BaseEntity;
import com.cokapp.quick.core.orm.jpa.BaseDao;
import com.cokapp.quick.core.orm.query.QueryObject;
import com.cokapp.quick.core.web.PageAdapter;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年9月2日 上午8:58:29
 */
@Transactional()
public abstract class SimpleQueryService<T extends BaseEntity<ID>, ID extends Serializable>
		implements ISimpleQuery<T, ID> {

	protected BaseDao<T, ID> baseDao;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public long count(QueryObject<T> para) {
		return this.baseDao.count(para.getSpecification());
	}

	@Override
	@Transactional(readOnly = true)
	public long countAll() {
		return this.baseDao.count();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ID id) {
		return this.baseDao.exists(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList() {
		return this.baseDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(QueryObject<T> para) {
		return this.baseDao.findAll(para.getSpecification(), para.getSortable());
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Sort sort) {
		return this.baseDao.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(final String property, final Object value) {
		Specification<T> spec = new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.equal(root.get(property), value);
			}
		};

		return this.baseDao.findAll(spec);
	}

	@Override
	@Transactional(readOnly = true)
	public T findOne(ID id) {
		return this.baseDao.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public T findOne(ID id, String... initLazyPropertyNames) {
		Assert.notNull(id);
		T entity = this.baseDao.findOne(id);
		this.lazyDetach(entity, initLazyPropertyNames);
		return entity;
	}

	@Override
	@Transactional(readOnly = true)
	public T findOne(String property, Object value) {
		List<T> entities = this.findList(property, value);
		if (CollectionUtils.isEmpty(entities)) {
			return null;
		} else {
			Assert.isTrue(entities.size() == 1);
			return entities.get(0);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageAdapter<T> findPage(Pageable pageable) {
		Page<T> page = this.baseDao.findAll(pageable);

		return PageAdapter.adapte(page);
	}

	@Override
	@Transactional(readOnly = true)
	public PageAdapter<T> findPage(QueryObject<T> para, String... initLazyPropertyNames) {
		Page<T> page = this.baseDao.findAll(para.getSpecification(), para.getPageable());

		Iterator<T> it = page.getContent().iterator();
		while (it.hasNext()) {
			T entity = it.next();
			this.lazyDetach(entity, initLazyPropertyNames);
		}

		return PageAdapter.adapte(page);
	}

	public BaseDao<T, ID> getBaseDao() {
		return this.baseDao;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected final void lazyDetach(T entity, String... initLazyPropertyNames) {
		if (entity == null) {
			return;
		}
		if (initLazyPropertyNames != null && initLazyPropertyNames.length > 0) {
			for (String name : initLazyPropertyNames) {
				try {
					Object propValue = MethodUtils.invokeMethod(entity, "get" + StringUtils.capitalize(name));
					if (propValue != null && propValue instanceof Collection<?>) {
						((Collection<?>) propValue).size();
					} else if (propValue != null && propValue instanceof BaseEntity<?>) {
						((BaseEntity<?>) propValue).getId();
					}
				} catch (Exception e) {
					throw new ServiceException("error.init.detached.entity", e);
				}
			}
		}
		this.getEntityManager().detach(entity);
	}

}
