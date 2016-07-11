package com.cokapp.quick.core.web.meta.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageMetaVO implements Serializable {
	public final static String KEY_IN_MODEL = "pageMeta";
	private static final long serialVersionUID = 9213355886794260342L;

	private String description = "无描述！";
	private Map<String, Object> extra = new HashMap<String, Object>();
	private String title = "无标题";
	private SimpleUser user;
}
