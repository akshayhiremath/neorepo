/**
 * 
 */
package com.akshay.client.neo.rest.exception;

/**
 * Thrown when any problem occurs in the REST web service call. Specifics of the
 * problem are described in the message of exception or in the cause. Exception
 * may contain a response/error code for standard or agreed errors.
 * 
 * @author AKSHAYH
 *
 */
public class RestClientException extends Exception {

	private static final long serialVersionUID = 5305018123559474806L;
	private String responseCode;
	private String responseMessage;

	/**
	 * Constructs new RestClientException with error message.
	 * 
	 * @param message
	 *            error message.
	 */

	public RestClientException(final String message) {
		super(message);
	}

	/**
	 * Constructs new RestClientException with error message and also set underlying
	 * cause.
	 * 
	 * @param message - error message.
	 * @param cause - underlying cause exception
	 */
	public RestClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs new RestClientException with error message and an error/response
	 * code.
	 * 
	 * @param responseMessage - error message.
	 * @param responseCode - error code
	 *            
	 */
	public RestClientException(final String responseMessage, final String responseCode) {
		super(responseMessage);
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(final String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(final String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
