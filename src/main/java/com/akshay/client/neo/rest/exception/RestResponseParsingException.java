/**
 * 
 */
package com.akshay.client.neo.rest.exception;

/**
 * Thrown by ResponseParserUtil if there is any problem in processing the raw
 * response of the REST client.
 * 
 * @author AKSHAYH
 *
 */
public class RestResponseParsingException extends Exception {

	private static final long serialVersionUID = -2243752148506514202L;
	private String message;

	/**
	 * Constructs new RestResponseParsingException with error message.
	 * 
	 * @param message
	 *            error message.
	 */
	public RestResponseParsingException(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
