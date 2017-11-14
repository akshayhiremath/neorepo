/**
 * 
 */
package com.akshay.client.neo.rest.exception;

/**
 * @author AKSHAYH
 *
 */
public class RestResponseParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2243752148506514202L;
	private String message;

	public RestResponseParsingException() {

	}

	public RestResponseParsingException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
