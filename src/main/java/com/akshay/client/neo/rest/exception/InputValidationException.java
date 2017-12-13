/**
 * 
 */
package com.akshay.client.neo.rest.exception;

/**
 * Thrown when inputs to the main NeoUtility are invalid.
 * 
 * @author AKSHAYH
 *
 */
public class InputValidationException extends Exception {

	private static final long serialVersionUID = -5408425114842952377L;

	private String message;

	/**
	 * Constructs new InputValidationException with error message.
	 * 
	 * @param message
	 *            error message.
	 */

	public InputValidationException(final String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
