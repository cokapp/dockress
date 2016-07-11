/**
 *
 */
package com.cokapp.quick.module.auth.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cokapp.cokits.core.annotation.MetaData;
import com.cokapp.quick.core.entity.BaseNativeEntity;
import com.cokapp.quick.core.helper.IDHelper;
import com.cokapp.quick.module.oauth.entity.OAuthUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年8月27日 下午3:09:40
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "auth_user")
public class User extends BaseNativeEntity {

	private static final long serialVersionUID = -5318387544159388653L;

	@MetaData(value = "用户头像")
	@Column(length = 200)
	private String avatar;

	@MetaData(value = "关联的社交账号集合")
	@OneToMany(mappedBy = "bindTo", fetch = FetchType.EAGER)
	private Set<OAuthUser> binds = Sets.newHashSet();

	@MetaData(value = "电子邮件")
	@Column(length = 100, unique = true)
	private String email;

	@MetaData(value = "电子邮件已经认证")
	@Column(name = "emailverified")
	private boolean emailVerified = false;

	@MetaData(value = "账号全局唯一标识", comments = "同时作为SYS类型用户登录密码的SALT")
	@Column(name = "guid", length = 64, nullable = false, unique = true)
	private String guid = IDHelper.genUID();

	@MetaData(value = "最近认证失败时间")
	private Date lastLogonFailureTime;

	@MetaData(value = "最后登录主机名")
	private String lastLogonHost;

	@MetaData(value = "最后登录IP")
	private String lastLogonIP;

	@MetaData(value = "最后登录时间")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLogonTime;

	@MetaData(value = "最近认证失败次数", comments = "认证失败累加，成功后清零。达到设定失败次数后锁定帐号，防止无限制次数尝试猜测密码")
	private Integer logonFailureTimes = 0;

	@MetaData(value = "总计登录次数")
	private Integer logonTimes = 0;

	@MetaData(value = "用户名已经修改")
	@Column(name = "nameupdated")
	private boolean nameupdated = false;

	@MetaData(value = "登录后友好显示昵称")
	@Column(name = "nickname", length = 100)
	private String nickname;

	@MetaData(value = "用户密码", comments = "加密算法：MD5({authGuid}+原始密码)")
	private String password;

	@MetaData(value = "角色集合")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "auth_user_role", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private Set<Role> roles;

	@MetaData(value = "用户名")
	@Column(length = 100, unique = true)
	private String username;

	public User() {
	}

	public User(Long id) {
		super(id);
	}
}
