/**
 *
 */
package com.cokapp.quick.core.web.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.cokapp.quick.core.entity.BaseEntity;
import com.cokapp.quick.core.orm.query.QueryBuilder;
import com.cokapp.quick.core.orm.query.QueryObject;
import com.cokapp.quick.core.service.BaseService;
import com.cokapp.quick.core.web.PageAdapter;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月13日 下午5:22:18
 */
public abstract class BaseController<T extends BaseEntity<ID>, ID extends Serializable> {
	protected List<T> findList(HttpServletRequest request) {
		QueryBuilder<T> builder = QueryBuilder.create(request);
		QueryObject<T> para = builder.build();
		List<T> rst = this.getBaseService().findList(para);
		return rst;
	}

	protected PageAdapter<T> findPage(HttpServletRequest request) {
		QueryBuilder<T> builder = QueryBuilder.create(request);
		QueryObject<T> para = builder.build();
		PageAdapter<T> rst = this.getBaseService().findPage(para);
		return rst;
	}

	protected abstract BaseService<T, ID> getBaseService();

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyyMMdd HH:mm:ss"), true));
	}
}
