package com.akshay.client.neo.rest.exception;

public class NeoProcessorException extends Exception {

	/**
	* 
	*/
	private static final long serialVersionUID = -228248092787381058L;
	private String message;

	public NeoProcessorException() {

	}

	public NeoProcessorException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
