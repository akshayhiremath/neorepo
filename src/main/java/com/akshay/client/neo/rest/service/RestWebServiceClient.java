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
 * Near Earth Object (NEO) data fetch utility
 * 
 * @author AKSHAYH
 *
 */
public class RestWebServiceClient {

	private static final Logger logger = Logger.getLogger(RestWebServiceClient.class);

	private Client jerseyRestClient;

	public RestWebServiceClient() {
		// Initializing RestClient
		initializeRestClient();
	}

	public RestWebServiceClient(Client jerseyRestClient) {
		super();
		this.jerseyRestClient = jerseyRestClient;
		// Initializing RestClient
		initializeRestClient();
	}

	public Client getJerseyRestClient() {
		return jerseyRestClient;
	}

	public void setJerseyRestClient(Client jerseyRestClient) {
		this.jerseyRestClient = jerseyRestClient;
	}

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
				
				if(response.getStatusInfo().getStatusCode()==429){
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
			} else{
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

	private void initializeRestClient() {
		if (getJerseyRestClient() == null) {
			setJerseyRestClient(Client.create());
		}
		getJerseyRestClient().setReadTimeout(6000);// 6 secs Time to read the blocks
		getJerseyRestClient().setConnectTimeout(60000);// 60 secs Time to connect
	}

}
