package com.lunchtech.vlbs.common.model.xinge;

public class XingeRespObj {
	public static final int RET_SUCCESS = 0;
	//参数错误,请对照错误提示和文档检查请求参数
	public static final int RET_PARAM_ERR = -1;
	//请求时间戳不在有效期内
	public static final int RET_TS_ERR = -2;
	//sign 校验无效,检查 access id 和 secret key(注意不是 access key)
	public static final int RET_SIGN_ERR = -3;
	//参数错误,请对照文档检查请求参数
	public static final int RET_PARAM_ERR_2 = 2;
	//推送的 token 没有在信鸽中注册,请检查终端注册是否成功
	public static final int RET_TOKEN_ERR = 40;
	//推送的账号没有在信鸽中注册,请检查终端注册是否成功
	public static final int RET_ACCOUNT_ERR = 48;
	//消息字符数超限,请减少消息内容再试
	public static final int RET_MSG_LENGTH_ERR = 73;
	//请求过于频繁,请稍后再试
	public static final int RET_FRI_ERR = 76;
	//循环任务参数错误
	public static final int RET_CRI_ERR = 78;
	//APNS 证书错误。请重新提交正确的证书
	public static final int RET_APNS_ERR = 100;
	private int ret_code;
	private String err_msg;
	private Object result;
	public int getRet_code() {
		return ret_code;
	}
	public void setRet_code(int ret_code) {
		this.ret_code = ret_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
