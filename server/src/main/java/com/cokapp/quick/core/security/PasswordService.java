package com.cokapp.quick.core.security;

import com.cokapp.cokits.util.Digests;
import com.cokapp.cokits.util.Encodes;

public class PasswordService {

	public static final String HASH_ALGORITHM = "MD5";
	public static final int SALT_SIZE = 8;

	public static String entryptPassword(String rawPassword) {
		return PasswordService.entryptPassword(rawPassword, PasswordService.generateSalt());
	}

	public static String entryptPassword(String rawPassword, String salt) {
		byte[] hashPassword = null;
		if (salt == null) {
			hashPassword = Digests.md5(rawPassword.getBytes());
		} else {
			hashPassword = Digests.md5((PasswordService.injectPasswordSalt(rawPassword, salt)).getBytes());
		}
		return Encodes.encodeHex(hashPassword);
	}

	private static String generateSalt() {
		byte[] salt = Digests.generateSalt(PasswordService.SALT_SIZE);
		return Encodes.encodeHex(salt);
	}

	/**
	 * 对原始密码注入盐值
	 */
	private static String injectPasswordSalt(String rawPassword, String salt) {
		return "{" + salt + "}" + rawPassword;
	}
}
