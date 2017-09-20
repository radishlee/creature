package com.lesports.gene.vct.common.exception;
/**
 * 当抛出这个异常时，提示客户端重试访问当前访问的接口
 * @author zhoujie
 *
 */
public class RetryException extends BaseException {

	private static final long serialVersionUID = 5133549700071598818L;

	public RetryException(String msg) {
		super(msg);
	}
	
	public RetryException() {}

}
