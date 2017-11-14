/**
 * 
 */
package com.akshay.client.neo.rest.exception;

/**
 * @author AKSHAYH
 *
 */
public class RestClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5305018123559474806L;
	private String responseCode;
	private String responseMessage;

	public RestClientException() {
		// Autogenetared constructor stub.
	}

	public RestClientException(final String message) {
		super(message);
	}

	public RestClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RestClientException(final String responseMessage, final String responseCode) {
		super(responseMessage);
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
