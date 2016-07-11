/**
 *
 */
package com.cokapp.quick.module.oauth.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2016年1月21日 上午11:17:45
 */
@Service
public class OAuthServcies {
	@Autowired
	private List<OAuthServiceDecorator> oAuthServiceDeractors;

	public OAuthServiceDecorator getOAuthService(final OAuthTypeEnum oAuthType) {
		Iterable<OAuthServiceDecorator> rst = Iterables.filter(this.oAuthServiceDeractors,
				new Predicate<OAuthServiceDecorator>() {
					@Override
					public boolean apply(OAuthServiceDecorator input) {
						return input.getOAuthType().equals(oAuthType);
					}
				});

		return rst.iterator().next();
	}
}
