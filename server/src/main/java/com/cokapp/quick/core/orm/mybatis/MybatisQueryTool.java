/**
 *
 */
package com.cokapp.quick.core.orm.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.cokapp.quick.core.web.utils.SpringContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 使用mybatis提供基本查询，注意为和hibernate共存，mybatis并未开启缓存
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年4月22日 下午3:16:41
 */
public final class MybatisQueryTool {

	public static <E> List<E> findLimitList(String statement, Map<String, Object> parameters, Integer top) {
		if (top != null) {
			RowBounds rowBounds = new RowBounds(0, top);
			return MybatisQueryTool.getSqlSession().selectList(statement, parameters, rowBounds);
		} else {
			return MybatisQueryTool.getSqlSession().selectList(statement, parameters);
		}
	}

	public static <E> List<E> findList(String statement, Map<String, Object> parameters) {
		return MybatisQueryTool.findLimitList(statement, parameters, null);
	}

	public static <E> Map<String, E> findMap(String statement, Map<String, Object> parameters, String mapKey) {
		return MybatisQueryTool.findMap(statement, parameters, mapKey, null);
	}

	public static <E> Map<String, E> findMap(String statement, Map<String, Object> parameters, String mapKey,
			Integer top) {
		if (top != null) {
			RowBounds rowBounds = new RowBounds(0, top);
			return MybatisQueryTool.getSqlSession().selectMap(statement, parameters, mapKey, rowBounds);
		} else {
			return MybatisQueryTool.getSqlSession().selectMap(statement, parameters, mapKey);
		}
	}

	public static <E> E findOne(String statement, Map<String, Object> parameters) {
		return MybatisQueryTool.getSqlSession().selectOne(statement, parameters);
	}

	public static <E> Page<E> findPage(String statement, Map<String, Object> parameters, Pageable pageable) {
		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());
		List<E> rows = MybatisQueryTool.getSqlSession().selectList(statement, parameters);

		PageInfo<E> pageInfo = new PageInfo<E>(rows);
		Page<E> page = new PageImpl<E>(rows, pageable, pageInfo.getTotal());

		return page;
	}

	public static <E> List<E> findSortList(String statement, Map<String, Object> parameters, Sort sort) {
		if (sort != null) {
			PageHelper.startPage(1, Integer.MAX_VALUE, sort.toString());
		}
		return MybatisQueryTool.getSqlSession().selectList(statement, parameters);
	}

	public static SqlSession getSqlSession() {
		return SpringContextUtils.getBean(SqlSessionTemplate.class);
	}

}
