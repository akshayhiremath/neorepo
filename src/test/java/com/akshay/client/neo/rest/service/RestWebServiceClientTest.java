package com.akshay.client.neo.rest.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.akshay.client.neo.rest.exception.RestClientException;
import com.akshay.client.neo.rest.utils.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.spi.factory.MessageBodyFactory;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Near Earth Object (NEO) data fetch utility
 * 
 * @author AKSHAYH
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ClientResponse.class })
public class RestWebServiceClientTest {

	private RestWebServiceClient wsClient = null;
	private Client mockJerseyClient = null;
	private WebResource mockWebResource = null;
	private Builder mockBuilder = null;
	private ClientResponse mockClientResponse = null;
	private ClientResponse.Status mockStatus = null;
	private StatusType mockStatusInfo = null;

	@Before
	public void setUp() {

		wsClient = new RestWebServiceClient();

		mockJerseyClient = mock(Client.class);
		wsClient.setJerseyRestClient(mockJerseyClient);
		mockWebResource = mock(WebResource.class);
		mockBuilder = mock(Builder.class);
		mockClientResponse = mock(ClientResponse.class);
		mockStatusInfo = mock(StatusType.class);
		// Static mocks
		mockStatic(ClientResponse.class);
		mockStatic(Status.class);

		when(mockJerseyClient.resource(anyString())).thenReturn(mockWebResource);
		when(mockWebResource.queryParams((MultivaluedMap<String, String>) any(MultivaluedMap.class))).thenReturn(mockWebResource);
		when(mockWebResource.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
		when(mockBuilder.header("content-type", MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);

	}

	@Test
	public void testCallRestWebServiceSuccess() {
		try {
			// 200 OK
			when(mockBuilder.get(ClientResponse.class)).thenReturn(mockClientResponse);
			when(mockClientResponse.getStatusInfo()).thenReturn(mockStatusInfo);
			when(mockStatusInfo.getStatusCode()).thenReturn(200);
			when(ClientResponse.Status.fromStatusCode(200)).thenReturn(ClientResponse.Status.OK);
			when(mockClientResponse.getEntity(any(Class.class))).thenReturn(new String("{ SUCCESS }"));
			String response = (String) wsClient.callRestWebService(Constants.FEED_SERVICE_TODAY,
					new MultivaluedMapImpl(), Class.class);

			assertEquals("{ SUCCESS }", response);

		} catch (RestClientException ex) {
			fail("Exception is not expected");
		}
	}

	@Test
	public void testCallRestWebServiceNasaServiceHttpResponseFailed() {

		try {
			// Possible HTTP failures from NASA API are,
			// 401 Unauthorized
			// 403 Forbidden
			// 404 Not Found
			// But we are wrapping all these scenarios in RestClientException so
			// our method behaviour is going to be same for other HTTP level
			// failures
			when(mockBuilder.get(ClientResponse.class)).thenReturn(mockClientResponse);
			when(mockClientResponse.getStatusInfo()).thenReturn(mockStatusInfo);
			when(mockStatusInfo.getStatusCode()).thenReturn(401);
			when(ClientResponse.Status.fromStatusCode(401)).thenReturn(ClientResponse.Status.UNAUTHORIZED);

			wsClient.callRestWebService(Constants.FEED_SERVICE_TODAY,
					new MultivaluedMapImpl(), Class.class);

			fail("RestClientException is expected for HTTP response 401");

		} catch (RestClientException ex) {
			assertTrue(ex.getMessage().contains("Call to REST Web Service failed with  HTTP Status: "));
			assertTrue(ex.getMessage().contains("401"));
		}

	}

	@Test
	public void testCallRestWebServiceJerseyClientUniformInterfaceException() {

		try {
			when(mockBuilder.get(ClientResponse.class)).thenReturn(mockClientResponse);
			when(mockClientResponse.getStatusInfo()).thenReturn(mockStatusInfo);
			when(mockClientResponse.toString()).thenReturn("This is mock Client Response");// For
																							// the
																							// UniformInterfaceException's
																							// parameterized
																							// constructor
			doNothing().when(mockClientResponse).bufferEntity();// For the
																// UniformInterfaceException's
																// parameterized
																// constructor
			when(mockStatusInfo.getStatusCode()).thenReturn(204);
			when(ClientResponse.Status.fromStatusCode(204)).thenReturn(ClientResponse.Status.NO_CONTENT);
			UniformInterfaceException uniformInterfaceException = mock(UniformInterfaceException.class);
			when(uniformInterfaceException.getMessage()).thenReturn("Uniform Interface Exception");
			when(mockClientResponse.getEntity(String.class)).thenThrow(uniformInterfaceException);
			String response = (String) wsClient.callRestWebService(Constants.FEED_SERVICE_TODAY,
					new MultivaluedMapImpl(), Class.class);

			fail("RestClientException is expected for HTTP response 204");
		} catch (RestClientException ex) {

			assertTrue(ex.getMessage().contains("REST Web Service Response is Empty."));
		}

	}

	@Test
	public void testCallRestWebServiceJerseyClientClientHandlerException() {
		try {
			when(mockBuilder.get(ClientResponse.class)).thenReturn(mockClientResponse);
			when(mockClientResponse.getStatusInfo()).thenReturn(mockStatusInfo);
			when(mockStatusInfo.getStatusCode()).thenReturn(200);
			// Random status(code and message) is used here.
			// The point is we will get ClientHandlerException if there is a
			// problem while processing the HTTP Response object content in
			// Jersey's getEntity
			// This means HTTP interaction is SUCESSFUL. Means whether HTTP
			// response is OK or any FAILURE (e.g. 403,404 etc.), it is conveyed
			// successfully at HTTP layer
			// but there is a problem in processing the Response object
			// received.
			// This MUST be handled at our REST client level and shouldn't be
			// exposed as it is to the REST WS client user/consumer

			when(ClientResponse.Status.fromStatusCode(200)).thenReturn(ClientResponse.Status.OK);
			when(mockClientResponse.getEntity(any(Class.class))).thenThrow(new ClientHandlerException());

			wsClient.callRestWebService(Constants.FEED_SERVICE_TODAY,
					new MultivaluedMapImpl(), Class.class);

			fail("RestClientException is expected when Client Handler has failed to process the Response to form Java object in ClientResponse.getEntity");

		} catch (RestClientException ex) {
			assertTrue(ex.getMessage().contains("Error processing REST Web Service Response content."));
		}
	}

}
