package com.cokapp.dockress.web.utils;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;
import org.springframework.web.context.ServletContextAware;

/**
 * Spring的工具类，用来获得配置文件中的bean
 * <p>
 * 在Spring配置文件注入
 *
 * @author dev@cokapp.com
 * @date 2014年3月8日 下午11:04:57
 */
public class SpringContextUtils implements ApplicationContextAware, ServletContextAware, EmbeddedValueResolverAware {
	private static ApplicationContext applicationContext = null;

	private static ServletContext servletContext = null;

	private static StringValueResolver stringValueResolver;

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 *
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return SpringContextUtils.applicationContext.containsBean(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 *
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return SpringContextUtils.applicationContext.getAliases(name);
	}

	public static ApplicationContext getApplicationContext() {
		return SpringContextUtils.applicationContext;
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return SpringContextUtils.applicationContext.getBean(requiredType);
	}

	/***
	 * 根据一个bean的id获取配置文件中相应的bean
	 *
	 * @param name
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return SpringContextUtils.applicationContext.getBean(name);
	}

	/***
	 * 类似于getBean(String name)只是在参数中提供了需要返回到的类型。
	 *
	 * @param name
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return SpringContextUtils.applicationContext.getBean(name, requiredType);
	}

	/**
	 * 读取Spring管理的属性文件值
	 *
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		return SpringContextUtils.stringValueResolver.resolveStringValue(name);
	}

	public static ServletContext getServletContext() {
		return SpringContextUtils.servletContext;
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return SpringContextUtils.applicationContext.getType(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 *
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return SpringContextUtils.applicationContext.isSingleton(name);
	}

	public static void registerBean(String beanId, String fullclass, Map<String, Object> pvs) {
		BeanDefinition bdef = new GenericBeanDefinition();
		bdef.setBeanClassName(fullclass);
		if (pvs != null) {
			for (String p : pvs.keySet()) {
				bdef.getPropertyValues().add(p, pvs.get(p));
			}
		}
		DefaultListableBeanFactory fty = (DefaultListableBeanFactory) SpringContextUtils.applicationContext
				.getAutowireCapableBeanFactory();
		fty.registerBeanDefinition(beanId, bdef);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringContextUtils.applicationContext == null) {
			SpringContextUtils.applicationContext = applicationContext;
			System.out.println();
			System.out.println();
			System.out.println("---------------------------------------------------------------------");
			System.out.println("======== spring context config successful(" + applicationContext + ")========");
			System.out.println("---------------------------------------------------------------------");
			System.out.println();
			System.out.println();
		}
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		SpringContextUtils.stringValueResolver = resolver;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		SpringContextUtils.servletContext = servletContext;
	}
}