/**
 *
 */
package com.cokapp.tongbutie.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cokapp.quick.core.entity.BaseNativeEntity;
import com.cokapp.quick.module.auth.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "biz_clipsubscribe", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "pk_clipboard", "pk_user" }) })
public class ClipSubscribe extends BaseNativeEntity {

	private static final long serialVersionUID = -8615470877646543230L;

	@ManyToOne(targetEntity = Clipboard.class)
	@JoinColumn(name = "pk_clipboard")
	private long clipboard;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "pk_user")
	private long user;

	public ClipSubscribe() {
	}

	public ClipSubscribe(Long id) {
		super(id);
	}
}
