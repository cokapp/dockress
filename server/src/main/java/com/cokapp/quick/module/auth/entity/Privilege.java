/**
 *
 */
package com.cokapp.quick.module.auth.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cokapp.quick.core.entity.BaseNativeEntity;
import com.cokapp.quick.core.json.EntitySerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年9月24日 下午4:21:02
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "auth_privilege")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "roles" })
public class Privilege extends BaseNativeEntity {
	private static final long serialVersionUID = 4029765815573751179L;
	@Column(length = 20, nullable = false, unique = true)
	private String code;
	@Column(length = 20)
	private String icon;
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pk_parent")
	@JsonSerialize(using = EntitySerializer.class)
	private Privilege parent;
	/**
	 * 以逗号分隔
	 */
	@Column()
	private String pids;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "auth_role_privilege", joinColumns = {
			@JoinColumn(name = "privilege_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private Set<Role> roles;

	@Column()
	private Integer status;

	@Column(length = 20, name = "typecode")
	// TODO 使用mapper时可能在拼接sql时有问题
	private String typeCode;

	@Column(length = 100)
	private String url;

	@Column()
	private Integer weight;

	public Privilege() {
	}

	public Privilege(Long id) {
		super(id);
	}
}
