package com.cokapp.quick.core.orm;

import java.io.Serializable;

import com.cokapp.quick.core.entity.BaseEntity;

public interface MiniDao<T extends BaseEntity<ID>, ID extends Serializable> {

}