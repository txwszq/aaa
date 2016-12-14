/**
 * @(#)BusinessRuntimeException.java   2013年11月19日
 * Copyright 2013  it.kedacom.com, Inc. All rights reserved.
 */
package com.kedacom.expenses.exception;

import org.springframework.util.StringUtils;

/**
 * 自定义异常类,所有的业务异常均抛出该异常类,controller捕获到该异常类,则返回Message.
 * 
 * @author HuajianXu
 * @version 2013年11月19日
 */
public class BusinessRuntimeException extends RuntimeException {

	/** serialVersionUID. */
	private static final long serialVersionUID = 6243565855570364010L;

	public BusinessRuntimeException() {
	}

	public BusinessRuntimeException(String message) {
		super(message);
	}

	public BusinessRuntimeException(Throwable th) {
		super(th);
	}

	public BusinessRuntimeException(String message, Throwable th) {
		super(message, th);
	}

	public String getMessage() {
		String message = super.getMessage();
		Throwable cause = getCause();
		if (!StringUtils.hasText(message) && cause != null) {
			return cause.getMessage();
		}
		return message;
	}
}
