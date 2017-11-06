package com.lunchtech.vlbs.service.xinge;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lunchtech.vlbs.common.exception.InternalException;
import com.lunchtech.vlbs.common.model.xinge.XingeRespObj;
import com.lunchtech.vlbs.common.utils.HttpUtil;
/**
 * 具体的测试case请看XingeAppTest，请在使用任何一个方法前请写好集成测试
 * @author zhoujie
 *
 */
@Service
public class XingeApp {
	//接口列表
	public static final String RESTAPI_PUSHSINGLEDEVICE = "http://openapi.xg.qq.com/v2/push/single_device";
	public static final String RESTAPI_PUSHSINGLEACCOUNT = "http://openapi.xg.qq.com/v2/push/single_account";
	public static final String RESTAPI_PUSHACCOUNTLIST = "http://openapi.xg.qq.com/v2/push/account_list";
	public static final String RESTAPI_PUSHALLDEVICE = "http://openapi.xg.qq.com/v2/push/all_device";
	public static final String RESTAPI_PUSHTAGS = "http://openapi.xg.qq.com/v2/push/tags_device";
	public static final String RESTAPI_QUERYPUSHSTATUS = "http://openapi.xg.qq.com/v2/push/get_msg_status";
	public static final String RESTAPI_QUERYDEVICECOUNT = "http://openapi.xg.qq.com/v2/application/get_app_device_num";
	public static final String RESTAPI_QUERYTAGS = "http://openapi.xg.qq.com/v2/tags/query_app_tags";
	public static final String RESTAPI_CANCELTIMINGPUSH = "http://openapi.xg.qq.com/v2/push/cancel_timing_task";
	public static final String RESTAPI_BATCHSETTAG = "http://openapi.xg.qq.com/v2/tags/batch_set";
	public static final String RESTAPI_BATCHDELTAG = "http://openapi.xg.qq.com/v2/tags/batch_del";
	public static final String RESTAPI_QUERYTOKENTAGS = "http://openapi.xg.qq.com/v2/tags/query_token_tags";
	public static final String RESTAPI_QUERYTAGTOKENNUM = "http://openapi.xg.qq.com/v2/tags/query_tag_token_num";
	public static final String RESTAPI_CREATEMULTIPUSH = "http://openapi.xg.qq.com/v2/push/create_multipush";
	public static final String RESTAPI_PUSHACCOUNTLISTMULTIPLE = "http://openapi.xg.qq.com/v2/push/account_list_multiple";
	public static final String RESTAPI_PUSHDEVICELISTMULTIPLE = "http://openapi.xg.qq.com/v2/push/device_list_multiple";
	public static final String RESTAPI_QUERYINFOOFTOKEN = "http://openapi.xg.qq.com/v2/application/get_app_token_info";
	public static final String RESTAPI_QUERYTOKENSOFACCOUNT = "http://openapi.xg.qq.com/v2/application/get_app_account_tokens";

	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";

	public static final int DEVICE_ALL = 0;
	public static final int DEVICE_BROWSER = 1;
	public static final int DEVICE_PC = 2;
	public static final int DEVICE_ANDROID = 3;
	public static final int DEVICE_IOS = 4;
	public static final int DEVICE_WINPHONE = 5;

	public static final int IOSENV_PROD = 1;
	public static final int IOSENV_DEV = 2;
	@Value("${xg.android.accessId}")
	private long androidAccessId;
	@Value("${xg.android.secretKey}")
	private String androidSecretKey;
	@Value("${xg.ios.accessId}")
	private long iosAccessId;
	@Value("${xg.ios.secretKey}")
	private String iosSecretKey;
	@Autowired
	private HttpClient client;
	@Value("${xg.ios.env}")
	private int iosEnv;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	// 易用的api接口v1.1.4引入
	public JSONObject pushTokenAndroid(String title, String content,
			String token) {
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);
		JSONObject ret = pushSingleDevice(token, message);
		return (ret);
	}

	public JSONObject pushAccountAndroid(String title, String content,
			String account) {
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);
		JSONObject ret = pushSingleAccount(0, account, message);
		return (ret);
	}

	public JSONObject pushAllAndroid(String title, String content) {
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);

		JSONObject ret = pushAllDevice(0, message);
		return (ret);
	}

	public JSONObject pushTagAndroid(String title, String content, String tag) {
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);

		List<Object> tagList = new ArrayList<Object>();
		tagList.add(tag);
		JSONObject ret = pushTags(0, tagList, "OR", message);
		return (ret);
	}

	public JSONObject pushTokenIos(String content, String token) {
		MessageIOS message = new MessageIOS();
		message.setAlert(content);
		message.setBadge(1);
		message.setSound("beep.wav");

		JSONObject ret = pushSingleDevice(token, message);
		return (ret);
	}

	public JSONObject pushAccountIos(String content, String account) {
		MessageIOS message = new MessageIOS();
		message.setAlert(content);
		message.setBadge(1);
		message.setSound("beep.wav");

		JSONObject ret = pushSingleAccount(0, account, message);
		return (ret);
	}

	public JSONObject pushAllIos(String content) {
		MessageIOS message = new MessageIOS();
		message.setAlert(content);
		message.setBadge(1);
		message.setSound("beep.wav");

		JSONObject ret = pushAllDevice(0, message);
		return (ret);
	}

	public JSONObject pushTagIos(String content, String tag) {
		MessageIOS message = new MessageIOS();
		message.setAlert(content);
		message.setBadge(1);
		message.setSound("beep.wav");

		List<Object> tagList = new ArrayList<Object>();
		tagList.add(tag);
		JSONObject ret = pushTags(0, tagList, "OR", message);
		return (ret);
	}

	// 详细的api接口
	public JSONObject pushSingleDevice(String deviceToken, Message message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.androidAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("multi_pkg", message.getMultiPkg());
		params.put("device_token", deviceToken);
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);

		return callRestful(XingeApp.RESTAPI_PUSHSINGLEDEVICE, params, this.androidSecretKey);
	}

	public JSONObject pushSingleDevice(String deviceToken, MessageIOS message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.iosAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("device_token", deviceToken);
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);
		params.put("environment", iosEnv);

		if (message.getLoopInterval() > 0 && message.getLoopTimes() > 0) {
			params.put("loop_interval", message.getLoopInterval());
			params.put("loop_times", message.getLoopTimes());
		}

		return callRestful(XingeApp.RESTAPI_PUSHSINGLEDEVICE, params, this.iosSecretKey);
	}

	public JSONObject pushSingleAccount(int deviceType, String account,
			Message message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.androidAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("multi_pkg", message.getMultiPkg());
		params.put("device_type", deviceType);
		params.put("account", account);
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);

		return callRestful(XingeApp.RESTAPI_PUSHSINGLEACCOUNT, params, this.androidSecretKey);
	}

	public JSONObject pushAccountList(int deviceType, List<Object> accountList,
			Message message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.androidAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("multi_pkg", message.getMultiPkg());
		params.put("device_type", deviceType);
		params.put("account_list", new JSONArray(accountList).toString());
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);

		return callRestful(XingeApp.RESTAPI_PUSHACCOUNTLIST, params, this.androidSecretKey);
	}

	public JSONObject pushSingleAccount(int deviceType, String account,
			MessageIOS message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.iosAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("device_type", deviceType);
		params.put("account", account);
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);
		params.put("environment", iosEnv);

		return callRestful(XingeApp.RESTAPI_PUSHSINGLEACCOUNT, params, this.iosSecretKey);
	}

	public JSONObject pushAccountList(int deviceType, List<Object> accountList,
			MessageIOS message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.iosAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("device_type", deviceType);
		params.put("account_list", new JSONArray(accountList).toString());
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);
		params.put("environment", iosEnv);

		return callRestful(XingeApp.RESTAPI_PUSHACCOUNTLIST, params, this.iosSecretKey);
	}

	public JSONObject pushAllDevice(int deviceType, Message message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.androidAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("multi_pkg", message.getMultiPkg());
		params.put("device_type", deviceType);
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);

		if (message.getLoopInterval() > 0 && message.getLoopTimes() > 0) {
			params.put("loop_interval", message.getLoopInterval());
			params.put("loop_times", message.getLoopTimes());
		}

		return callRestful(XingeApp.RESTAPI_PUSHALLDEVICE, params, this.androidSecretKey);
	}

	public JSONObject pushAllDevice(int deviceType, MessageIOS message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.iosAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("device_type", deviceType);
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);
		params.put("environment", iosEnv);

		if (message.getLoopInterval() > 0 && message.getLoopTimes() > 0) {
			params.put("loop_interval", message.getLoopInterval());
			params.put("loop_times", message.getLoopTimes());
		}

		return callRestful(XingeApp.RESTAPI_PUSHALLDEVICE, params, this.iosSecretKey);
	}

	public JSONObject pushTags(int deviceType, List<Object> tagList,
			String tagOp, Message message) {
		if (!message.isValid() || tagList.size() == 0
				|| (!tagOp.equals("AND") && !tagOp.equals("OR"))) {
			throw new InternalException("param invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.androidAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("multi_pkg", message.getMultiPkg());
		params.put("device_type", deviceType);
		params.put("message_type", message.getType());
		params.put("tags_list", new JSONArray(tagList).toString());
		params.put("tags_op", tagOp);
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);

		if (message.getLoopInterval() > 0 && message.getLoopTimes() > 0) {
			params.put("loop_interval", message.getLoopInterval());
			params.put("loop_times", message.getLoopTimes());
		}

		return callRestful(XingeApp.RESTAPI_PUSHTAGS, params, this.androidSecretKey);
	}

	public JSONObject pushTags(int deviceType, List<Object> tagList,
			String tagOp, MessageIOS message) {
		if (!message.isValid() || tagList.size() == 0
				|| (!tagOp.equals("AND") && !tagOp.equals("OR"))) {
			throw new InternalException("param invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.iosAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("send_time", message.getSendTime());
		params.put("device_type", deviceType);
		params.put("message_type", message.getType());
		params.put("tags_list", new JSONArray(tagList).toString());
		params.put("tags_op", tagOp);
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);
		params.put("environment", iosEnv);

		if (message.getLoopInterval() > 0 && message.getLoopTimes() > 0) {
			params.put("loop_interval", message.getLoopInterval());
			params.put("loop_times", message.getLoopTimes());
		}

		return callRestful(XingeApp.RESTAPI_PUSHTAGS, params, this.iosSecretKey);
	}

	public JSONObject createMultipush(Message message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.androidAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("multi_pkg", message.getMultiPkg());
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);

		return callRestful(XingeApp.RESTAPI_CREATEMULTIPUSH, params, this.androidSecretKey);
	}

	public JSONObject createMultipush(MessageIOS message) {
		if (!message.isValid()) {
			throw new InternalException("message invalid!");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_id", this.iosAccessId);
		params.put("expire_time", message.getExpireTime());
		params.put("message_type", message.getType());
		params.put("message", message.toJson());
		params.put("timestamp", System.currentTimeMillis() / 1000);
		params.put("environment", iosEnv);

		return callRestful(XingeApp.RESTAPI_CREATEMULTIPUSH, params, this.iosSecretKey);
	}

	protected String generateSign(String method, String url,
			Map<String, Object> params, String secretKey) {
		List<Map.Entry<String, Object>> paramList = new ArrayList<Map.Entry<String, Object>>(
				params.entrySet());
		Collections.sort(paramList,
				new Comparator<Map.Entry<String, Object>>() {
					public int compare(Map.Entry<String, Object> o1,
							Map.Entry<String, Object> o2) {
						return (o1.getKey()).toString().compareTo(o2.getKey());
					}
				});
		String paramStr = "";
		String md5Str = "";
		for (Map.Entry<String, Object> entry : paramList) {
			paramStr += entry.getKey() + "=" + entry.getValue().toString();
		}
		try {
			URL u = new URL(url);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String s = method + u.getHost() + u.getPath() + paramStr
					+ secretKey;
			byte[] bArr = s.getBytes("UTF-8");
			byte[] md5Value = md5.digest(bArr);
			BigInteger bigInt = new BigInteger(1, md5Value);
			md5Str = bigInt.toString(16);
			while (md5Str.length() < 32) {
				md5Str = "0" + md5Str;
			}
			// System.out.println(s);
		} catch (Exception e) {
			// e.printStackTrace();
			return "";
		}
		return md5Str;
	}

	protected JSONObject callRestful(String url, Map<String, Object> params, String secretKey) {
		JSONObject jsonRet = null;
		String sign = generateSign("POST", url, params, secretKey);
		if (sign.isEmpty()) {
			throw new InternalException("sign is error");
		}
		params.put("sign", sign);
		HttpPost post = new HttpPost(url);
		// post.addHeader("Content-Type",
		// "application/x-www-form-urlencoded;charset=utf-8");
		List<BasicNameValuePair> data = new ArrayList<BasicNameValuePair>();
		for (Entry<String, Object> entry : params.entrySet()) {
			data.add(new BasicNameValuePair(entry.getKey(), entry.getValue()
					.toString()));
		}

		try {
			post.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));

			HttpResponse response = client.execute(post,
					HttpClientContext.create());
			int status = response.getStatusLine().getStatusCode();
			if (status != HttpStatus.SC_OK) {
				String resp = HttpUtil.getResponseBody(response);
				this.logger.warn(resp);
				throw new InternalException("系统错误，请稍后重试");
			}
			String resp = HttpUtil.getResponseBody(response);
			jsonRet = (JSONObject) JSON.parse(resp);
			if (jsonRet.getInteger("ret_code") != XingeRespObj.RET_SUCCESS) {
				this.logger.warn(resp);
				throw new InternalException("系统错误，请稍后重试");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new InternalException("系统错误，请稍后重试");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new InternalException("系统错误，请稍后重试");
		} catch (IOException e) {
			e.printStackTrace();
			throw new InternalException("系统错误，请稍后重试");
		}

		return jsonRet;
	}


}
