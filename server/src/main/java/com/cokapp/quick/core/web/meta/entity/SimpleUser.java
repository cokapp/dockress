package com.cokapp.quick.core.web.meta.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 简单用户对象，用于存放通用的用户数据，便于前台使用
 * 
 * @author heichengliang@talkweb.com.cn
 * @date 2015年5月20日 下午5:01:40
 */
@Setter
@Getter
public class SimpleUser implements Serializable {
	private static final long serialVersionUID = -4716629193663614152L;

	private String email;
	private String nickName;
	private Byte status;
	private String username;
}
