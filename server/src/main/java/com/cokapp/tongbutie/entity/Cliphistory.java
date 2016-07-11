/**
 *
 */
package com.cokapp.tongbutie.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cokapp.quick.core.entity.BaseNativeEntity;
import com.cokapp.quick.core.json.EntitySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月16日 上午8:48:46
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "biz_cliphistory")
public class Cliphistory extends BaseNativeEntity {

	private static final long serialVersionUID = 8748177226312495728L;

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pk_clipboard")
	@JsonSerialize(using = EntitySerializer.class)
	private Clipboard clipboard;

	// 内容
	@Column(columnDefinition = "TEXT")
	private String content;

	public Cliphistory() {
	}

	public Cliphistory(Long id) {
		super(id);
	}
}
