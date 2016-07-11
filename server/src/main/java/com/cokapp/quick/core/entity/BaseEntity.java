package com.cokapp.quick.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

import com.cokapp.cokits.core.annotation.MetaData;
import com.cokapp.cokits.core.lang.exception.ErrorCode;
import com.cokapp.cokits.core.lang.exception.ExceptionUtils;
import com.cokapp.cokits.util.mapper.JsonMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Access(AccessType.FIELD)
@Accessors(chain = true)
@JsonIgnoreProperties(value = { "new", "version", "hibernateLazyInitializer", "javassistLazyInitializer",
		"handler" }, ignoreUnknown = true)
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
	private static final long serialVersionUID = 2476761516236455260L;

	@Column(name = "createdby", length = 100, insertable = true, updatable = false)
	private String createdBy;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "createddate", insertable = true, updatable = false)
	private Date createdDate = new Date();

	private Boolean del = Boolean.FALSE;

	@Transient
	private String extra;

	@Transient
	@Getter(value = lombok.AccessLevel.NONE)
	@Setter(value = lombok.AccessLevel.NONE)
	private ObjectNode jsonExtra = JsonNodeFactory.instance.objectNode();

	@Column(name = "modifiedby", length = 100)
	private String modifiedBy;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonInclude(Include.NON_NULL)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "modifieddate")
	private Date modifiedDate = new Date();

	@MetaData(value = "乐观锁版本")
	@Version
	@Column(name = "version", nullable = false)
	private Integer version = 0;

	public BaseEntity() {
	}

	public BaseEntity(ID id) {
		this.setId(id);
	}

	@Transient
	public String getDisplay() {
		return this.toString();
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "extra", columnDefinition = "TEXT")
	public String getExtra() {
		return this.extra;
	}

	public JsonNode getExtra(String key) {
		return this.jsonExtra.get(key);
	}

	public abstract ID getId();

	public boolean isNew() {
		return this.getId() == null;
	}

	public void putExtra(String key, JsonNode value) {
		this.jsonExtra.put(key, value);
		this.writeExtra();
	}

	private void readJsonExtra(String extra) {
		if (!StringUtils.isEmpty(extra)) {
			try {
				this.jsonExtra = (ObjectNode) JsonMapper.getInstance().readTree(extra);
			} catch (Exception e) {
				ExceptionUtils.wrapBiz(e, ErrorCode.BIZ, "extra字段格式错误【必须存储Json格式字符串】！");
			}
		}
	}

	public void removeExtra(String key) {
		this.jsonExtra.remove(key);
		this.writeExtra();
	}

	public void setExtra(String extra) {
		this.readJsonExtra(extra);
		this.extra = extra;
	}

	public abstract void setId(final ID id);

	@Override
	public String toString() {
		return this.getId() + "@" + this.getClass().getSimpleName();
	}

	private void writeExtra() {
		this.extra = this.jsonExtra.toString();
	}
}