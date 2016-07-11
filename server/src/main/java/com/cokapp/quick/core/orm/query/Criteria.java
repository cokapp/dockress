package com.cokapp.quick.core.orm.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class Criteria<T> implements Specification<T> {
	private List<Criterion> criterions = new ArrayList<Criterion>();

	/**
	 * 增加简单条件表达式
	 *
	 * @Methods Name add
	 * @Create In 2012-2-8 By lee
	 * @param expression0
	 *            void
	 */
	public void add(Criterion criterion) {
		if (criterion != null) {
			this.criterions.add(criterion);
		}
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (!this.criterions.isEmpty()) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			for (Criterion c : this.criterions) {
				predicates.add(c.toPredicate(root, query, builder));
			}

			// 将所有条件用 and 联合起来
			if (predicates.size() > 0) {
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}
		return builder.conjunction();
	}
}