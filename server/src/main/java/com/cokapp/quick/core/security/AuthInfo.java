/**
 *
 */
package com.cokapp.quick.core.security;

import java.io.Serializable;

import com.cokapp.cokits.core.lang.exception.ExceptionUtils;
import com.cokapp.cokits.util.Encodes;
import com.cokapp.cokits.util.mapper.JsonMapper;
import com.cokapp.cokits.util.security.Coder;
import com.cokapp.cokits.util.security.DESCoder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月19日 下午5:47:23
 */
@Getter
@Setter
public class AuthInfo implements Serializable {
	private static String KEY = "tongbutie-12345678901234";

	private final static JsonMapper mapper = JsonMapper.getInstance();
	private static final long serialVersionUID = -6166105751880174090L;

	static {
		try {
			AuthInfo.KEY = Coder.encryptStrToBASE64(AuthInfo.KEY);
		} catch (Exception e) {
			ExceptionUtils.wrapBiz(e);
		}
	}

	public static AuthInfo deSerialize(String token) {

		try {
			// 1.HEX
			byte[] hex = Encodes.decodeHex(token);
			// 2.解密
			byte[] bytes = DESCoder.decrypt(hex, AuthInfo.KEY);

			// 3.反序列化
			AuthInfo tokenObject = AuthInfo.mapper.readValue(bytes, AuthInfo.class);

			// 4.有效性检查
			if (tokenObject.expireIn > 0 && tokenObject.expireTime < System.currentTimeMillis()) {
				ExceptionUtils.wrapBiz("令牌【%s】已失效！", token);
			}

			return tokenObject;
		} catch (Exception e) {
			ExceptionUtils.wrapBiz(e);
			return null;
		}

	}

	/**
	 * 生成一个永不过期的token，慎用
	 *
	 * @param guid
	 * @return
	 */
	public static AuthInfo gen(String guid) {
		return AuthInfo.gen(guid, 0);
	}

	/**
	 * 生成一个expireIn毫秒后过期的token
	 * 
	 * @param guid
	 * @param expireIn
	 * @return
	 */
	public static AuthInfo gen(String guid, long expireIn) {
		AuthInfo info = new AuthInfo();
		info.setGuid(guid);
		info.setExpireIn(expireIn);
		return info;
	}

	/**
	 * 存在时间，单位秒
	 */
	private long expireIn = 7200;
	/**
	 * 失效时间
	 */
	@Setter(AccessLevel.NONE)
	private long expireTime = System.currentTimeMillis() + this.expireIn * 1000;
	private String guid;

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}

		AuthInfo that = (AuthInfo) obj;

		return that.getExpireTime() == this.getExpireTime() && this.getGuid().equals(this.getGuid());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public String serialize() {
		// 1.序列化
		String json = AuthInfo.mapper.toJson(this);
		try {
			// 2.加密
			byte[] bytes = DESCoder.encrypt(json.getBytes(), AuthInfo.KEY);
			// 3.HEX
			return Encodes.encodeHex(bytes);
		} catch (Exception e) {
			ExceptionUtils.wrapBiz(e);
			return null;
		}
	}

	public void setExpireIn(long expireIn) {
		this.expireIn = expireIn;
		this.expireTime = System.currentTimeMillis() + this.expireIn * 1000;
	}

}
