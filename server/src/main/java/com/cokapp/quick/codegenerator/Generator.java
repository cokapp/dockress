/**
 *
 */
package com.cokapp.quick.codegenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月12日 下午4:47:00
 */
public class Generator {
	public static Logger logger = LoggerFactory.getLogger(Generator.class);

	private Configuration cfg;
	private Config genCfg;
	private Meta meta;

	public Generator(Configuration cfg, Config genCfg, Meta meta) {
		this.cfg = cfg;
		this.meta = meta;
		this.genCfg = genCfg;
	}

	private boolean continueGen(File targetFile) {
		if (!targetFile.exists()) {
			return true;
		}
		if (this.genCfg.isReplaceAll()) {
			return true;
		}

		// 当前仅按包名判断，生成目录的文件默认替换
		if (targetFile.getAbsolutePath().contains("_gen")) {
			return true;
		}

		return false;
	}

	public void gen(String tplName) {
		try {
			Template template = this.cfg.getTemplate(tplName);
			String fileName = this.parseFileName(tplName, this.meta);

			String fullFileName = this.genCfg.getTargetDir() + File.separator + fileName;
			File targetFile = new File(fullFileName);
			if (!this.continueGen(targetFile)) {
				Generator.logger.debug(String.format("文件【%s】已存在，跳过！", fullFileName));
				return;
			}

			File newsDir = targetFile.getParentFile();
			if (!newsDir.exists()) {
				newsDir.mkdirs();
			}
			OutputStream os = new FileOutputStream(fullFileName);
			Writer out = new OutputStreamWriter(os, "UTF-8");

			HashMap<String, Object> root = Maps.newHashMap();
			root.put("meta", this.meta);
			root.put("date", new DateTime());

			template.process(root, out);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 将包含占位符的模板文件名解析成生成文件路径
	 *
	 * @param tplName
	 * @param meta
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private String parseFileName(String tplName, Meta meta) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clz = Meta.class;
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getName().toLowerCase();
			String value = field.get(meta).toString();

			tplName = tplName.replace("{" + name + "}", value);
		}

		tplName = tplName.replace(".ftl", "");
		tplName = tplName.replaceAll("\\.", "\\" + File.separator);
		tplName = tplName.substring(0, tplName.length() - 5);
		tplName = tplName + ".java";

		return tplName;
	}
}
