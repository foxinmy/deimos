package com.foxinmy.deimos.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liubin
 *
 */
public class BusinessException extends RuntimeException {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
