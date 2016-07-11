/**
 *
 */
package com.cokapp.quick.codegenerator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.google.common.collect.Sets;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月12日 下午3:02:50
 */
public class GenerateDirector {

	public static void main(String[] args) {
		Config cfg = new Config("D:\\JAVA\\Workspace\\cokclip\\server\\src\\main\\java");
		// cfg.setReplaceAll(true);
		cfg.setBasePackage("com.cokapp.quick.module.auth");
		// cfg.setBasePackage("com.cokapp.tongbutie");

		new GenerateDirector(cfg).gen();
	}

	private final Config genCfg;

	private GenerateDirector(Config cfg) {
		this.genCfg = cfg;
	}

	/**
	 * 根据实体类构造代码生成元数据
	 *
	 * entityName = foo.bar.entity.Foobar
	 *
	 * @param entityName
	 * @return
	 */
	private Meta buildMeta(String entityName) {
		Meta meta = new Meta();
		try {
			String packageName = StringUtils.substringBeforeLast(entityName, ".entity");
			String className = StringUtils.substringAfterLast(entityName, ".");

			Class<?> entityClass = Class.forName(entityName);
			String idTypeName = entityClass.getMethod("getId").getReturnType().getSimpleName();

			meta.setClassName(className);
			meta.setPackageName(packageName);
			meta.setIdTypeName(idTypeName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return meta;
	}

	public void gen() {
		Configuration cfg = new Configuration();
		// 设置FreeMarker的模版文件位置
		cfg.setClassForTemplateLoading(GenerateDirector.class, this.genCfg.getTplPath());
		cfg.setDefaultEncoding("UTF-8");

		Set<String> entityNames = this.scanEntity(this.genCfg.getBasePackage());
		for (String entityName : entityNames) {
			try {
				this.gen(cfg, entityName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void gen(Configuration cfg, String entityName)
			throws TemplateException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException {

		Meta meta = this.buildMeta(entityName);
		URL root = GenerateDirector.class.getResource("tpls");

		Generator generator = new Generator(cfg, this.genCfg, meta);
		this.traverseFolder(new File(root.getFile()), new File(root.getFile()), generator);
	}

	/**
	 * 扫描目标包内所有@Entity
	 *
	 * @return
	 */
	private Set<String> scanEntity(String... basePackages) {
		Set<String> entityNames = Sets.newHashSet();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));

		for (String basePackage : basePackages) {
			Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(basePackage);
			for (BeanDefinition beanDefinition : beanDefinitions) {
				entityNames.add(beanDefinition.getBeanClassName());
			}
		}

		return entityNames;
	}

	private void traverseFolder(File file, File root, Generator generator) {

		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				return;
			} else {
				for (File file2 : files) {

					if (file2.isDirectory()) {
						this.traverseFolder(file2, root, generator);
					} else {
						String name = file2.getAbsolutePath().replace(root.getAbsolutePath(), "");
						name = name.substring(1, name.length());
						generator.gen(name);
					}
				}
			}
		}
	}
}
