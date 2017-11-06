package com.lunchtech.vlbs.admin.model;

public class ResponseDataModel {

	private Meta meta;
	private Object data;
	public static final ResponseDataModel SUCCESS() {
		
		ResponseDataModel rs = new ResponseDataModel();
		Meta meta = rs.getMeta();
		meta.setCode(Meta.CODE_SUCCESS);
		
		return rs;
	}
	
	public static final ResponseDataModel ERROR() {
		ResponseDataModel rs = new ResponseDataModel();
		Meta meta = rs.getMeta();
		meta.setCode(Meta.CODE_ERROR);
		return rs;
	}
	
	public static final ResponseDataModel RETRY() {
		ResponseDataModel rs = new ResponseDataModel();
		Meta meta = rs.getMeta();
		meta.setCode(Meta.CODE_RETRY);
		return rs;
	}
	public ResponseDataModel() {
		this.meta = new Meta();
	}
	
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public static class Meta {
		//请求成功
		public static final int CODE_SUCCESS = 200;
		//请求失败
		public static final int CODE_ERROR = 400;
		//请求重试该接口
		public static final int CODE_RETRY = 401;
		private String error_type;
		private int code;
		private String error_message;
		public String getError_type() {
			return error_type;
		}
		public void setError_type(String error_type) {
			this.error_type = error_type;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getError_message() {
			return error_message;
		}
		public void setError_message(String error_message) {
			this.error_message = error_message;
		}
		
	}
}
