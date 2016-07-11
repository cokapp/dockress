/**
 *
 */
package com.cokapp.quick.module.auth.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cokapp.quick.core.entity.BaseNativeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2015年8月27日 下午4:25:15
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "auth_role")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "users" })
public class Role extends BaseNativeEntity {
	private static final long serialVersionUID = 5313308519208754843L;
	@Column(length = 20, nullable = false, unique = true)
	private String code;
	@Column(length = 20, nullable = false)
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "auth_role_privilege", joinColumns = {
			@JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "privilege_id", nullable = false, updatable = false) })
	private Set<Privilege> privileges;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "auth_user_role", joinColumns = {
			@JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", nullable = false, updatable = false) })
	private Set<User> users;

	public Role() {
	}

	public Role(Long id) {
		super(id);
	}

}
