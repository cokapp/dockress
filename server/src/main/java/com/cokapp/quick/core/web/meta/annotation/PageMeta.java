package com.cokapp.quick.core.web.meta.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入页面元数据注解
 * 
 * @author dev@cokapp.com
 * @date 2015年11月16日 下午10:34:45
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PageMeta {
	String description() default "";

	String title() default "";
}