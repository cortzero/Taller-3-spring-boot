package com.example.main.exceptions;

public class URLWithoutProtocolException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public URLWithoutProtocolException(String message) {
		super(message);
	}

}
