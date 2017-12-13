package com.akshay.client.neo.rest.exception;

/**
 * Thrown when any problem occurs in NeoProcessor initialization or operations.
 * Specifics of the problem are described in the message of exception.
 * 
 * @author AKSHAYH
 *
 */
public class NeoProcessorException extends Exception {

	private static final long serialVersionUID = -228248092787381058L;
	private String message;

	/**
	 * Constructs new NeoProcessorException with error message.
	 * 
	 * @param message
	 *            error message.
	 */
	public NeoProcessorException(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
