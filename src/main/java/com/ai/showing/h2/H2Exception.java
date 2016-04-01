package com.ai.showing.h2;

/**
 * 自定义H2错误
 * @author Luorj
 *
 */
public class H2Exception extends RuntimeException {

	private static final long serialVersionUID = -4159809233886128425L;

	public H2Exception() {
		super();
	}

	public H2Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public H2Exception(String message) {
		super(message);
	}

	public H2Exception(Throwable cause) {
		super(cause);
	}

}
