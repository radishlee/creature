package com.lunchtech.vlbs.common.exception;

public class StorageFileNotFoundException extends BaseException {

	public StorageFileNotFoundException(String msg) {
		super(msg);
	}
	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	private static final long serialVersionUID = -4691243260135431459L;

}