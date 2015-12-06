package com.jumbo.components;

public class JumboException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JumboException() {

	}

	public JumboException(String message) {
		super(message);
	}

	public JumboException(Throwable cause) {
		super(cause);
	}

	public JumboException(String message, Throwable cause) {
		super(message, cause);
	}

}
