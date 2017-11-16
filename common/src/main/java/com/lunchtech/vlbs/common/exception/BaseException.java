package com.lunchtech.vlbs.common.exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 6825586042142433816L;
	protected String msg;
	protected String type;
	public BaseException() {}
	public BaseException(String msg) {
		super(msg);
		this.msg = msg;
		this.type = this.getClass().getSimpleName();
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
