/**
 *
 */
package com.cokapp.quick.module.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.cokapp.cokits.core.annotation.MetaData;
import com.cokapp.quick.core.entity.BaseNativeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 用户注册表
 * 
 * @author heichengliang@talkweb.com.cn
 * @date 2016年3月14日 下午5:32:05
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "auth_user_register")
public class UserRegister extends BaseNativeEntity {
	private static final long serialVersionUID = 8505924253964518471L;
	@MetaData(value = "电子邮件")
	@Column(length = 100)
	private String email;
	@MetaData(value = "唯一哈希码")
	@Column(length = 100)
	private String hash;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "lastemaildate")
	private Date lastEmailDate = new Date();
	@MetaData(value = "密码")
	@Column(length = 100)
	private String password;

	@MetaData(value = "随机码")
	@Column(name = "randomcode", length = 10)
	private String randomCode;

}
