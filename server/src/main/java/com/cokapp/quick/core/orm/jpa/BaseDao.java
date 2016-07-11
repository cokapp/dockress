package com.cokapp.quick.core.orm.jpa;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.cokapp.quick.core.entity.BaseEntity;
import com.cokapp.quick.core.orm.MiniDao;

@NoRepositoryBean
public interface BaseDao<T extends BaseEntity<ID>, ID extends Serializable>
		extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, MiniDao<T, ID> {

}
