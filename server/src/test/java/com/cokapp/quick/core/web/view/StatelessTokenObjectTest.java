/**
 *
 */
package com.cokapp.quick.core.web.view;

import org.junit.Assert;
import org.junit.Test;

import com.cokapp.quick.core.security.AuthInfo;

/**
 *
 * @author dev@cokapp.com
 * @date 2015年10月20日 上午9:45:42
 */
public class StatelessTokenObjectTest {

	@Test
	public void d() {
		AuthInfo obj = new AuthInfo();
		obj.setExpireIn(10);
		obj.setGuid("1");

		// b4e4be0ac1552517a8d780e77038cf26ff83b9a36c39071b10e3d6e6aa445a528b9784cefc430b65
		String str = obj.serialize();

		AuthInfo obj2 = AuthInfo.deSerialize(str);

		Assert.assertEquals(obj, obj2);
	}

}
