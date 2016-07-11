/**
 *
 */
package com.cokapp.quick.core.orm.query;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.cokapp.quick.core.orm.query.Criterion.Operator;

/**
 * 查询条件构建器
 *
 * @author dev@cokapp.com
 * @date 2015年9月2日 上午9:13:51
 */
public class QueryBuilder<T> {

	private final static int PARA_DEFAULT_PAGE = 0;
	private final static int PARA_DEFAULT_SIZE = 20;
	private final static String PARA_PAGE_NAME = "page";
	private final static String PARA_SIZE_NAME = "size";
	// sort = email desc, username desc
	private final static String PARA_SORT_NAME = "sort";

	public static <T> QueryBuilder<T> create() {
		QueryBuilder<T> builder = new QueryBuilder<T>();
		builder.criteria = new Criteria<T>();
		return builder;
	}

	public static <T> QueryBuilder<T> create(HttpServletRequest request) {
		QueryBuilder<T> builder = QueryBuilder.<T> create()// 创建空查询
				.createPage(request)// 分页
				.createSort(request)// 排序
				.createQuery(request);// 查询条件

		return builder;
	}

	private static Map<String, String[]> getParameters(ServletRequest request, String prefix, String suffix) {
		Enumeration<?> paramNames = request.getParameterNames();
		Map<String, String[]> params = new TreeMap<String, String[]>();
		if (prefix == null) {
			prefix = "";
		}
		if (suffix == null) {
			suffix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if (("".equals(prefix) || paramName.startsWith(prefix))
					&& ("".equals(suffix) || paramName.endsWith(suffix))) {
				String unprefixed = paramName.substring(prefix.length(), paramName.length() - suffix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, new String[] { values[0] });
				}
			}
		}
		return params;
	}

	private Criteria<T> criteria;

	private SimplePageable pageable;

	private Sort sortable;

	/**
	 * 并且
	 *
	 * @param criterions
	 * @return
	 */
	public QueryBuilder<T> and(Criterion... criterions) {
		this.criteria.add(new LogicalExpression(criterions, Operator.AND));
		return this;
	}

	public QueryObject<T> build() {
		return QueryObject.create(this.criteria, this.pageable, this.sortable);
	}

	private QueryBuilder<T> createPage(HttpServletRequest request) {
		String p_page = request.getParameter(QueryBuilder.PARA_PAGE_NAME);
		String p_size = request.getParameter(QueryBuilder.PARA_SIZE_NAME);
		int page = p_page == null ? QueryBuilder.PARA_DEFAULT_PAGE : Integer.valueOf(p_page);
		int size = p_size == null ? QueryBuilder.PARA_DEFAULT_SIZE : Integer.valueOf(p_size);
		this.page(page, size);
		return this;
	}

	/**
	 * @todo 需要完善
	 * @param request
	 * @return
	 */
	private QueryBuilder<T> createQuery(HttpServletRequest request) {
		// 查询参数key:id_eq,value:id
		Map<String, String[]> searchParams = QueryBuilder.getParameters(request, "search['", "']");
		Iterator<Entry<String, String[]>> it = searchParams.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String[]> para = it.next();
			if (para.getValue() == null || para.getValue().length == 0) {
				continue;
			}

			String type = StringUtils.substringAfter(para.getKey(), "_");
			String fieldName = StringUtils.substringBefore(para.getKey(), "_");
			String value = para.getValue()[0];

			if (type == "like") {
				this.like(fieldName, value);
				continue;
			}

			// 反射调用包括gt，lt等方法
			try {
				Method method = this.getClass().getMethod(type, String.class, Object.class);
				method.invoke(this, fieldName, value);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return this;
	}

	private QueryBuilder<T> createSort(HttpServletRequest request) {
		String p_sort = request.getParameter(QueryBuilder.PARA_SORT_NAME);
		String[] sorts = null;
		if (p_sort == null) {
			sorts = new String[] {};
		} else {
			sorts = p_sort.split(",");
		}
		for (String sort : sorts) {
			sort = sort.trim();
			String direction = sort.split(" ")[1];
			String name = sort.split(" ")[0];
			this.sort(Direction.fromString(direction), name);
		}
		return this;
	}

	/**
	 * 等于
	 *
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public QueryBuilder<T> eq(String fieldName, Object value) {
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			this.criteria.add(new SimpleExpression(fieldName, value, Operator.EQ));
		}
		return this;
	}

	/**
	 * 大于
	 *
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public QueryBuilder<T> gt(String fieldName, Object value) {
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			this.criteria.add(new SimpleExpression(fieldName, value, Operator.GT));
		}

		return this;
	}

	/**
	 * 小于等于
	 *
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public QueryBuilder<T> gte(String fieldName, Object value) {
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			this.criteria.add(new SimpleExpression(fieldName, value, Operator.LTE));
		}

		return this;
	}

	/**
	 * 包含于
	 *
	 * @param fieldName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public QueryBuilder<T> in(String fieldName, Collection value) {
		if (value == null || value.isEmpty()) {
			return this;
		}
		SimpleExpression[] ses = new SimpleExpression[value.size()];
		int i = 0;
		for (Object obj : value) {
			ses[i] = new SimpleExpression(fieldName, obj, Operator.EQ);
			i++;
		}
		this.criteria.add(new LogicalExpression(ses, Operator.OR));

		return this;
	}

	/**
	 * 模糊匹配
	 *
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public QueryBuilder<T> like(String fieldName, String value) {
		if (!StringUtils.isEmpty(value)) {
			this.criteria.add(new SimpleExpression(fieldName, value, Operator.LIKE));
		}
		return this;
	}

	/**
	 * 小于
	 *
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public QueryBuilder<T> lt(String fieldName, Object value) {
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			this.criteria.add(new SimpleExpression(fieldName, value, Operator.LT));
		}
		return this;
	}

	/**
	 * 大于等于
	 *
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public QueryBuilder<T> lte(String fieldName, Object value) {
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			this.criteria.add(new SimpleExpression(fieldName, value, Operator.GTE));
		}
		return this;
	}

	/**
	 * 不等于
	 *
	 * @param fieldName
	 * @param value
	 * @param ignoreNull
	 * @return
	 */
	public QueryBuilder<T> ne(String fieldName, Object value) {
		if (StringUtils.isNotEmpty(fieldName) && value != null) {
			this.criteria.add(new SimpleExpression(fieldName, value, Operator.NE));
		}
		return this;
	}

	/**
	 * 或者
	 *
	 * @param criterions
	 * @return
	 */
	public QueryBuilder<T> or(Criterion... criterions) {
		this.criteria.add(new LogicalExpression(criterions, Operator.OR));

		return this;
	}

	public QueryBuilder<T> page(int page, int size) {
		this.pageable = new SimplePageable(page, size);
		this.pageable.setSort(this.sortable);
		return this;
	}

	public QueryBuilder<T> sort(Direction direction, String... properties) {
		Sort sort = new Sort(direction, properties);
		this.sort(sort);
		return this;
	}

	public QueryBuilder<T> sort(Sort sort) {
		if (this.sortable == null) {
			this.sortable = sort;
		} else {
			this.sortable.and(sort);
		}
		this.pageable.setSort(this.sortable);

		return this;
	}

}
