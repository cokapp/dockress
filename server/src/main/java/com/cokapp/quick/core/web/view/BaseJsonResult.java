package com.cokapp.quick.core.web.view;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cokapp.cokits.core.entity.query.ISimplePage;
import com.cokapp.cokits.util.mapper.JsonMapper;
import com.cokapp.cokits.util.security.Md5Utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.Getter;
import lombok.Setter;

public class BaseJsonResult<M> {
	@Setter
	@Getter
	static class PageInfo {
		public static PageInfo instance(ISimplePage<?> page) {
			PageInfo pager = new PageInfo();
			pager.setSize(page.getSize());
			pager.setNumber(page.getNumber());
			pager.setNumberOfElements(page.getNumberOfElements());
			pager.setTotalElements(page.getTotalElements());
			pager.setTotalPages(page.getTotalPages());
			pager.setFirstPage(page.isFirstPage());
			pager.setLastPage(page.isLastPage());
			return pager;
		}

		private Boolean firstPage;

		private Boolean lastPage;

		private Integer number;

		private Integer numberOfElements;

		private Integer size;

		private Long totalElements;

		private Integer totalPages;
	}

	@Setter
	@Getter
	static class Status {
		private int code;
		private String message;
		private boolean success;

		public Status() {
			this.code = 200;
			this.message = "处理成功!";
			this.success = true;
		}
	}

	private static Map<String, Class<?>> filterCache = new Hashtable<String, Class<?>>();
	@JsonIgnore
	protected String cbkey = null;
	@Getter
	protected Object data = null;

	@Getter
	@JsonIgnore
	protected Map<String, Object> extraData = null;
	@JsonIgnore
	@Getter
	private Map<Class<?>, Collection<String>> jsonFilterMap = Maps.newHashMap();
	@Getter
	protected PageInfo pageInfo = null;
	@Getter
	protected Status status = new Status();

	@SuppressWarnings("deprecation")
	private JsonMapper createJsonMapper(Map<Class<?>, Class<?>> map) {
		JsonMapper mapper = new JsonMapper(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
		Set<Entry<Class<?>, Class<?>>> entries = map.entrySet();
		for (Entry<Class<?>, Class<?>> entry : entries) {
			mapper.addMixInAnnotations(entry.getKey(), entry.getValue());
		}
		return mapper;
	}

	/**
	 * 动态生成Jackson属性过滤接口
	 *
	 * @param target
	 * @param names
	 * @return
	 */
	private Class<?> createMixInAnnotation(Class<?> target, Collection<String> properties) {
		Set<String> copyOfProperties = Sets.newHashSet(properties);
		JsonIgnoreProperties annotation = target.getAnnotation(JsonIgnoreProperties.class);
		if (annotation != null) {
			String[] targetFilter = annotation.value();
			for (String s : targetFilter) {
				copyOfProperties.add(s);
			}
		}
		String toHash = "";
		for (String s : copyOfProperties) {
			toHash += s + ",";
		}
		String md5 = Md5Utils.hash(toHash);
		if (BaseJsonResult.filterCache.containsKey(md5)) {
			return BaseJsonResult.filterCache.get(md5);
		}
		ClassPool pool = ClassPool.getDefault();
		// FIXME 当过滤的属性【properties】相同时生成MinxinAnnotation应做其他处理，此处添加时间戳区分有隐患
		CtClass cc = pool.makeInterface("MinxinAnnotation" + md5 + new Date().getTime());
		ClassFile ccFile = cc.getClassFile();
		ConstPool constpool = ccFile.getConstPool();
		// create the annotation
		AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
		// 创建JsonIgnoreProperties注解
		Annotation jsonIgnorePropertiesAnnotation = new Annotation(JsonIgnoreProperties.class.getName(), constpool);
		BooleanMemberValue ignoreUnknownMemberValue = new BooleanMemberValue(false, constpool);
		ArrayMemberValue arrayMemberValue = new ArrayMemberValue(constpool);// value的数组成员
		Collection<MemberValue> memberValues = new HashSet<MemberValue>();
		for (String name : copyOfProperties) {
			StringMemberValue memberValue = new StringMemberValue(constpool);// 将name值设入注解内
			memberValue.setValue(name);
			memberValues.add(memberValue);
		}
		arrayMemberValue.setValue(memberValues.toArray(new MemberValue[] {}));
		jsonIgnorePropertiesAnnotation.addMemberValue("value", arrayMemberValue);
		jsonIgnorePropertiesAnnotation.addMemberValue("ignoreUnknown", ignoreUnknownMemberValue);
		attr.addAnnotation(jsonIgnorePropertiesAnnotation);
		ccFile.addAttribute(attr);
		// generate the class
		Class<?> clazz = null;
		try {
			clazz = cc.toClass();
			BaseJsonResult.filterCache.put(md5, clazz);
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
		return clazz;

	}

	protected String toJson() {
		Map<Class<?>, Class<?>> filters = new HashMap<Class<?>, Class<?>>();
		Iterator<Entry<Class<?>, Collection<String>>> iterator = this.getJsonFilterMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Class<?>, Collection<String>> filter = iterator.next();
			Class<?> filterAnnotation = this.createMixInAnnotation(filter.getKey(), filter.getValue());
			filters.put(filter.getKey(), filterAnnotation);
		}
		JsonMapper mapper = this.createJsonMapper(filters);
		String json = mapper.toJson(this);
		return json;
	}

}
