/**
 *
 */
package com.cokapp.quick.codegenerator;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月13日 上午10:29:58
 */
@Getter
@Setter
public class Config {
	private String basePackage = "";
	private boolean replaceAll = false;
	private String targetDir = ".";
	private String tplPath = "tpls";

	public Config(String targetDir) {
		this.targetDir = targetDir;
	}
}
