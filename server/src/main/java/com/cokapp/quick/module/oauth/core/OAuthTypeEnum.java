package com.cokapp.quick.module.oauth.core;

import com.cokapp.cokits.core.lang.exception.ExceptionUtils;

import lombok.Getter;

@Getter
public enum OAuthTypeEnum {
	QQ("QQ互联", "qq"), WEIBO("新浪微博", "weibo"), WEIXIN("微信", "weixin");

	public static OAuthTypeEnum byName(String name) {
		for (OAuthTypeEnum oAuthType : OAuthTypeEnum.values()) {
			if (oAuthType.getName().equals(name)) {
				return oAuthType;
			}
		}
		ExceptionUtils.wrap("没有名称为【%s】的OAuthTypeEnum", name);
		return null;
	}

	private String displayName;
	private String name;

	// 构造方法
	private OAuthTypeEnum(String displayName, String name) {
		this.displayName = displayName;
		this.name = name;
	}

}