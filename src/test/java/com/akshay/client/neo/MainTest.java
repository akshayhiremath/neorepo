package com.akshay.client.neo;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.akshay.client.neo.rest.utils.Constants.usageError;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.akshay.client.neo.rest.exception.InputValidationException;
import com.akshay.client.neo.rest.exception.NeoProcessorException;
import com.akshay.client.neo.rest.exception.RestClientException;
import com.akshay.client.neo.rest.exception.RestResponseParsingException;
import com.akshay.client.neo.rest.model.NeoDataCollection;
import com.akshay.client.neo.rest.processor.NeoProcessor;
import com.akshay.client.neo.rest.service.RestWebServiceClient;
import com.akshay.client.neo.rest.utils.ResponseParserUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Main.class })
public class MainTest {

	Main main = null;
	private RestWebServiceClient mockRestClient = null;
	private ResponseParserUtil mockResponseParser = null;
	private NeoDataCollection mockNeoDataCollection = null;
	private NeoProcessor mockNeoProcessor = null;

	public MainTest() {

	}

	@Before
	public void setUp() {
		main = new Main();

		mockRestClient = mock(RestWebServiceClient.class);
		mockResponseParser = mock(ResponseParserUtil.class);
		mockNeoProcessor = mock(NeoProcessor.class);
		mockNeoDataCollection = mock(NeoDataCollection.class);

		main.setNeoDataCollection(mockNeoDataCollection);
		main.setRestClient(mockRestClient);
		main.setResponseParser(mockResponseParser);
		main.setNeoProcessor(mockNeoProcessor);
		
		//Suppressing unnecessary sysout during Unit testing
		PrintStream dummyStream = new PrintStream(new OutputStream(){
		    public void write(int b) {
		        // NO-OP
		    }
		});
		System.setOut(dummyStream);
		System.setErr(dummyStream);
	}

	@Test
	public void testRestWebServiceClientFailure() {
		try {
			String[] dateRange = { "2017-11-04", "2017-11-10" };
			when(mockRestClient.callRestWebService(anyString(), (MultivaluedMap<String, String>) anyObject(),
					(Class) anyObject())).thenThrow(new RestClientException("Exception in REST call."));
			main.execute(dateRange);

		} catch (RestClientException e) {
			fail("Exception should have handled in main.");
		} catch (InputValidationException e) {
			fail("Exception not expected.");
		}
	}

	@Test
	public void testNeoProcessorFailure() {
		try {
			String[] dateRange = { "2017-11-04", "2017-11-10" };
			when(mockNeoProcessor.initialize()).thenThrow(new NeoProcessorException("Exception in initialization"));
			main.execute(dateRange);

		} catch (NeoProcessorException e) {
			fail("Exception should have handled in main.");
		} catch (InputValidationException e) {
			fail("Exception not expected.");
		}
	}

	@Test
	public void testRestServiceJsonResponseParserFailed() {
		try {
			String[] dateRange = { "2017-11-04", "2017-11-10" };
			when(mockRestClient.callRestWebService(anyString(), (MultivaluedMap<String, String>) anyObject(),
					(Class) anyObject())).thenReturn("{ }");
			when(mockResponseParser.parseFeedServiceJsonResponse(anyString()))
					.thenThrow(new RestResponseParsingException("Exception in REST response parser."));
			when(mockNeoProcessor.initialize()).thenThrow(new NeoProcessorException("Exception in initialization"));
			main.execute(dateRange);

		} catch (RestResponseParsingException e) {
			assertTrue(e.getMessage().contains("Error while parsing JSON response of REST service."));
		} catch (RestClientException e) {
			fail("RestClientException is  not expected here.");
		} catch (NeoProcessorException e) {
			fail("NeoProcessorException is  not expected here.");
		} catch (InputValidationException e) {
			fail("Exception not expected.");
		}
	}
	
	@Test
	public void testValidationFailureOddMonthValue() {
		//month greater than 12
		String[] dateRange = { "2017-40-04", "2017-100-10" };
		try {
				main.execute(dateRange);
		}catch(InputValidationException ie) {
			assertTrue(ie.getMessage().contains(usageError));
		}catch(Exception e){
			fail("Exception not expected.");
		}
	}
		
	@Test
	public void testValidationFailureOutOfOrderDdMmYy() {
			//date month year out of order
			String[] dateRange = { "04-11-2017", "11-11-2017" };
			try {
					main.execute(dateRange);
			}catch(InputValidationException ie) {
				assertTrue(ie.getMessage().contains(usageError));
			}catch(Exception e){
				fail("Exception not expected.");
			}	
	}
	
	@Test
	public void testValidationFailureMoreThan7Days() {
			//period more than 7 days
			String[] dateRange = { "2017-11-04", "2017-12-04" };
			try {
					main.execute(dateRange);
			}catch(InputValidationException ie) {
				assertTrue(ie.getMessage().contains(usageError));
			}catch(Exception e){
				fail("Exception not expected.");
			}	
	}
	
	@Test
	public void testValidationFailureEndDateEarlierThanStartDate() {
			//start date 4 Dec and end date is 4 Nov
			String[] dateRange = { "2017-12-04", "2017-11-04" };
			try {
					main.execute(dateRange);
			}catch(InputValidationException ie) {
				assertTrue(ie.getMessage().contains(usageError));
			}catch(Exception e){
				fail("Exception not expected.");
			}	
	}

}
