/**
 *
 */
package com.cokapp.quick.core.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cokapp.cokits.core.lang.exception.ErrorCode;
import com.cokapp.cokits.core.lang.exception.ExceptionUtils;
import com.cokapp.cokits.util.mapper.JsonMapper;
import com.cokapp.quick.core.mail.entity.Mail;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

/**
 *
 * @author heichengliang@talkweb.com.cn
 * @date 2016年3月14日 下午4:42:33
 */
public class Sendcloud {

	private final static String API_SEND = "http://api.sendcloud.net/apiv2/mail/send";
	private final static String API_SEND_TEMPLATE = "http://api.sendcloud.net/apiv2/mail/sendtemplate";

	private final static String KEY = "vWXt17k38162FSp9";
	private final static String USER = "kuaitie";

	private static List<BasicNameValuePair> buildParams(String user, String key, Mail mail) {
		List<BasicNameValuePair> params = Lists.newArrayList();
		params.add(new BasicNameValuePair("apiUser", user));
		params.add(new BasicNameValuePair("apiKey", key));
		params.add(new BasicNameValuePair("from", mail.getForm()));
		params.add(new BasicNameValuePair("fromName", mail.getFromName()));
		params.add(new BasicNameValuePair("to", mail.getTo()));
		params.add(new BasicNameValuePair("subject", mail.getSubject()));
		return params;
	}

	private static boolean post(HttpPost httpost) {
		HttpClient httpclient = HttpClientBuilder.create().build();
		boolean rst = false;
		try {
			HttpResponse response = httpclient.execute(httpost);
			// 处理响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 读取xml文档
				String result = EntityUtils.toString(response.getEntity());
				JsonNode json = JsonMapper.getInstance().readTree(result);
				rst = json.get("statusCode").asInt() == 200;
			}
		} catch (Exception e) {
			ExceptionUtils.wrapBiz(e, ErrorCode.BIZ, "处理失败！");
		}
		httpost.releaseConnection();
		return rst;
	}

	public static boolean send(Mail mail) {
		return Sendcloud.send(Sendcloud.USER, Sendcloud.KEY, mail);
	}

	public static boolean send(String user, String key, Mail mail) {
		HttpPost httpost = new HttpPost(Sendcloud.API_SEND);
		List<BasicNameValuePair> params = Sendcloud.buildParams(user, key, mail);
		params.add(new BasicNameValuePair("html", mail.getHtml()));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.wrapBiz(e, ErrorCode.BIZ, "邮件发送失败！");
		}

		return Sendcloud.post(httpost);
	}

	public static boolean sendTemplate(String templateInvokeName, JsonNode xsmtpapi, Mail mail) {
		return Sendcloud.sendTemplate(Sendcloud.USER, Sendcloud.KEY, templateInvokeName, xsmtpapi, mail);
	}

	public static boolean sendTemplate(String user, String key, String templateInvokeName, JsonNode xsmtpapi,
			Mail mail) {
		HttpPost httpost = new HttpPost(Sendcloud.API_SEND_TEMPLATE);
		List<BasicNameValuePair> params = Sendcloud.buildParams(user, key, mail);
		params.add(new BasicNameValuePair("templateInvokeName", templateInvokeName));
		params.add(new BasicNameValuePair("xsmtpapi", xsmtpapi.toString()));
		try {
			httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.wrapBiz(e, ErrorCode.BIZ, "模板邮件发送失败！");
		}
		return Sendcloud.post(httpost);
	}

}
