/**
 *
 */
package com.cokapp.quick.core.mail.entity;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2016年3月14日 下午4:42:55
 */
@Getter
@Setter
public class Mail {
	private String form;
	private String fromName;
	private String html;
	private String subject;
	private String to;
}
