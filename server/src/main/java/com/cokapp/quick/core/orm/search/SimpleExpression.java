package com.cokapp.quick.core.orm.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

public class SimpleExpression implements Criterion {

	private String fieldName; // 属性名
	private Operator operator; // 计算符
	private Object value; // 对应值

	protected SimpleExpression(String fieldName, Object value, Operator operator) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public Operator getOperator() {
		return this.operator;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Path expression = null;
		if (this.fieldName.contains(".")) {
			String[] names = StringUtils.split(this.fieldName, ".");
			expression = root.get(names[0]);
			for (int i = 1; i < names.length; i++) {
				expression = expression.get(names[i]);
			}
		} else {
			expression = root.get(this.fieldName);
		}

		switch (this.operator) {
		case EQ:
			return builder.equal(expression, this.value);
		case NE:
			return builder.notEqual(expression, this.value);
		case LIKE:
			return builder.like(expression, "%" + this.value + "%");
		case LT:
			return builder.lessThan(expression, (Comparable) this.value);
		case GT:
			return builder.greaterThan(expression, (Comparable) this.value);
		case LTE:
			return builder.lessThanOrEqualTo(expression, (Comparable) this.value);
		case GTE:
			return builder.greaterThanOrEqualTo(expression, (Comparable) this.value);
		default:
			return null;
		}
	}

}