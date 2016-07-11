package com.cokapp.quick.core.orm.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @deprecated 使用{@code com.cokapp.quick.core.orm.mybatis.MybatisQueryTool}代替
 * @param <E>
 * @author dev@cokapp.com
 * @date 2015年9月1日 上午11:58:33
 */
@Deprecated
@Component
public class MybatisBaseDao<E> {

	@Autowired
	private SqlSession sqlSession;

	private String buildSqlId(String namespace, String statementId) {
		return namespace + "." + statementId;
	}

	public List<E> findLimitList(String namespace, String statementId, Map<String, Object> parameters, Integer top) {
		String statement = this.buildSqlId(namespace, statementId);
		if (top != null) {
			RowBounds rowBounds = new RowBounds(0, top);
			return this.sqlSession.selectList(statement, parameters, rowBounds);
		} else {
			return this.sqlSession.selectList(statement, parameters);
		}
	}

	public List<E> findList(String namespace, String statementId, Map<String, Object> parameters) {
		return this.findLimitList(namespace, statementId, parameters, null);
	}

	public Map<String, E> findMap(String namespace, String statementId, Map<String, Object> parameters, String mapKey) {
		return this.findMap(namespace, statementId, parameters, mapKey, null);
	}

	public Map<String, E> findMap(String namespace, String statementId, Map<String, Object> parameters, String mapKey,
			Integer top) {
		String statement = this.buildSqlId(namespace, statementId);
		if (top != null) {
			RowBounds rowBounds = new RowBounds(0, top);
			return this.sqlSession.selectMap(statement, parameters, mapKey, rowBounds);
		} else {
			return this.sqlSession.selectMap(statement, parameters, mapKey);
		}
	}

	public E findOne(String namespace, String statementId, Map<String, Object> parameters) {
		String statement = this.buildSqlId(namespace, statementId);
		return this.getSqlSession().selectOne(statement, parameters);
	}

	public Page<E> findPage(String namespace, String statementId, Map<String, Object> parameters, Pageable pageable) {
		String statement = this.buildSqlId(namespace, statementId);

		PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());
		List<E> rows = this.sqlSession.selectList(statement, parameters);

		PageInfo<E> pageInfo = new PageInfo<E>(rows);
		Page<E> page = new PageImpl<E>(rows, pageable, pageInfo.getTotal());

		return page;
	}

	public List<E> findSortList(String namespace, String statementId, Map<String, Object> parameters, Sort sort) {
		String statement = this.buildSqlId(namespace, statementId);
		if (sort != null) {
			PageHelper.startPage(1, Integer.MAX_VALUE, sort.toString());
		}
		return this.sqlSession.selectList(statement, parameters);
	}

	public SqlSession getSqlSession() {
		return this.sqlSession;
	}

}
