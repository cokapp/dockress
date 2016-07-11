/**
 *
 */
package com.cokapp.quick.module.oauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cokapp.cokits.core.annotation.MetaData;
import com.cokapp.quick.core.entity.BaseNativeEntity;
import com.cokapp.quick.module.auth.entity.User;
import com.cokapp.quick.module.oauth.core.OAuthTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2016年1月21日 上午10:21:41
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "oauth_user", uniqueConstraints = @UniqueConstraint(columnNames = { "oauthid", "oauthtype" }) )
@JsonIgnoreProperties(ignoreUnknown = true, value = { "bindTo" })
public class OAuthUser extends BaseNativeEntity {

	private static final long serialVersionUID = -5318387567959388653L;

	@MetaData(value = "绑定目标账号", comments = "用于OAUTH类型账号绑定到系统账号")
	@ManyToOne
	@JoinColumn(name = "bindto")
	private User bindTo;

	@MetaData(value = "账号类型所对应唯一标识")
	@Column(name = "oauthid", length = 64, nullable = false)
	private String oAuthId;

	@MetaData(value = "账号类型")
	@Column(name = "oauthtype", length = 8, nullable = false)
	@Enumerated(EnumType.STRING)
	private OAuthTypeEnum oAuthType;
}
