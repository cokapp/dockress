package com.cokapp.quick.core.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@MappedSuperclass
public class BaseNativeEntity extends BaseEntity<Long> {
	private static final long serialVersionUID = 693468696296687126L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private Long id;

	public BaseNativeEntity() {
	}

	public BaseNativeEntity(Long id) {
		super(id);
	}
}