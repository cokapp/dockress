/**
 *
 */
package com.cokapp.tongbutie.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cokapp.quick.core.entity.BaseNativeEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月16日 上午8:48:16
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "biz_clipboard")
public class Clipboard extends BaseNativeEntity {

	private static final long serialVersionUID = 3448529403351995420L;

	// 最新内容
	@Column(columnDefinition = "TEXT")
	private String content;

	// 描述
	@Column(length = 200)
	private String description;
	// 权限控制 1:公开 2:用户可查看 3:私有
	@Column(length = 1)
	private int mode = 1;
	// 剪贴板名称
	@Column()
	private String name = "默认剪贴板";
	// 短ID
	@Column(length = 6, nullable = true, unique = true)
	private String shortid;

	public Clipboard() {
	}

	public Clipboard(Long id) {
		super(id);
	}
}
