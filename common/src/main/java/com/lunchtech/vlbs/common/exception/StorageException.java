package com.lunchtech.vlbs.common.exception;

public class StorageException extends BaseException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
