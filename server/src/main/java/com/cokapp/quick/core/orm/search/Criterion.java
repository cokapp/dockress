package com.cokapp.quick.core.orm.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Criterion {
	public enum Operator {
		AND, EQ, GT, GTE, LIKE, LT, LTE, NE, OR
	}

	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}