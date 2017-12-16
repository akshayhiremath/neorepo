package com.akshay.client.neo.rest.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.akshay.client.neo.rest.exception.RestClientException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * A wrapper REST web service client. This client internally uses the open
 * source Jersey client implementation provided by Sun/Oracle. This client hides
 * the details of HTTP calls and other handling needed to make a REST calls and
 * obtaining a response. This exposes a simple REST call interface through
 * method
 * callRestWebService {@link com.akshay.client.neo.rest.service.RestWebServiceClient#callRestWebService(String, MultivaluedMap, Class)}.
 * 
 * @author AKSHAYH
 *
 */
public class RestWebServiceClient {

	private static final Logger logger = Logger.getLogger(RestWebServiceClient.class);
	
	private Client jerseyRestClient;

	public RestWebServiceClient() {
		// Initializing RestClient
		init();
	}

	public Client getJerseyRestClient() {
		return jerseyRestClient;
	}
	
	public void setJerseyRestClient(final Client jerseyRestClient) {
		this.jerseyRestClient = jerseyRestClient;
	}
	/**
	 * The user of the client just have to provide REST
	 * service URL, query parameters if any and a POJO class in which the response
	 * is expected to be encapsulated. If user is not sure of the response POJO structure or
	 * have a dynamic response with fields varying on the fly then he can pass
	 * String.class and parse the String response as per needs.
	 * @param serviceUrl - an URL of the REST service to hit
	 * @param paramMap - a map containing Query parameters key, value pairs
	 * @param responseClass - a class to which response is expected to be unmarshalled into
	 * @return REST service response object
	 * @throws RestClientException - An exception occurred while consuming the REST service. Details of problem are in the message of the exception. 
	 */
	public Object callRestWebService(final String serviceUrl, final MultivaluedMap<String, String> paramMap,
			final Class<?> responseClass) throws RestClientException {

		logger.debug("Initialized the Jersey REST Client");

		final Client client = getJerseyRestClient();
		Status restResponseStatus = null;

		final WebResource webResource = client.resource(serviceUrl);

		ClientResponse response = null;
		long startTime = 0L;
		try {
			logger.info("Calling REST Web Service. URL: " + serviceUrl);
			// Call to WebService
			startTime = System.currentTimeMillis();
			response = webResource.queryParams(paramMap).accept(MediaType.APPLICATION_JSON)
					.header("content-type", MediaType.APPLICATION_JSON).get(ClientResponse.class);
			long endTime = System.currentTimeMillis();
			logger.info("REST Web Service call completed. URL: " + serviceUrl + " Time taken: "
					+ ((endTime - startTime) / 1000) + " secs");

			if (response != null && response.getStatusInfo() != null) {

				if (response.getStatusInfo().getStatusCode() == 429) {
					throw new RestClientException("NASA api visit limit exceeded.");
				}
				restResponseStatus = ClientResponse.Status.fromStatusCode(response.getStatusInfo().getStatusCode());

			} else {
				final String restClientResponseMessage = "REST Web Service call failed. Response or the ResponseInfo is null.";
				logger.error(restClientResponseMessage);
				throw new RestClientException(restClientResponseMessage);
			}
			if (restResponseStatus.equals(Status.OK)) {
				logger.info("REST Web Service call SUCCEEDED.");
				return response.getEntity(responseClass);
			} else {
				// Consuming the response to avoid connection leakage
				response.getEntity(String.class);
				logger.error("Error occured in REST Web Service call. Response is not OK. Response is: "
						+ restResponseStatus.getReasonPhrase() + " : " + restResponseStatus);

				final String restClientResponseMessage = "Call to REST Web Service failed with  HTTP Status: "
						+ restResponseStatus.getStatusCode() + " " + restResponseStatus.getReasonPhrase();
				throw new RestClientException(restClientResponseMessage);
			}

		} catch (final UniformInterfaceException e) {
			logger.error("REST Web Service Response is Empty: " + e.getMessage());
			throw new RestClientException("REST Web Service Response is Empty.");
		} catch (final ClientHandlerException e) {
			logger.error("Error processing REST Web Service Response content: " + e.getMessage());
			throw new RestClientException("Error processing REST Web Service Response content.");
		}

	}
	
	/**
	 * Private method initializing the underlying Jersey client.
	 * Here the Sun Jersey client is instantiated and basic properties related to timeout are set.
	 */
	private void init() {
		if (getJerseyRestClient() == null) {
			setJerseyRestClient(Client.create());
		}
		getJerseyRestClient().setReadTimeout(6000);// 6 secs Time to read the blocks
		getJerseyRestClient().setConnectTimeout(60000);// 60 secs Time to connect
	}

}
