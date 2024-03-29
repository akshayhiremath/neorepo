/**
 * 
 */
package com.akshay.client.neo.rest.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.akshay.client.neo.rest.exception.RestResponseParsingException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author AKSHAYH
 *
 */

public class ResponseParserUtilTest {
	
	private ObjectMapper mockJacksonMapper = null;
//	private JsonParser mockGsonParser = null;
	private ResponseParserUtil jsonResponseParser = null;
	
	@Before
	public void setUp(){
		mockJacksonMapper = mock(ObjectMapper.class); 
		jsonResponseParser = new ResponseParserUtil();
		jsonResponseParser.setJacksonMapper(new ObjectMapper());
		jsonResponseParser.init();
	}

	
	@Test
	public void testMalformedJsonInputToparseFeedServiceJsonResponse(){
		
		try{
			jsonResponseParser.parseFeedServiceJsonResponse("{dfa}");
		}catch(RestResponseParsingException ex){
			assertTrue(ex.getMessage().contains("Error while parsing JSON response of REST service."));
		}
	}
	
	@Test
	public void testJaksonMapperFailsWithJsonMappingException(){
		try{
			jsonResponseParser.setJacksonMapper(mockJacksonMapper);
			String jsonReponseString = "{\"neoId\":\"345278\"}"; 
			when(mockJacksonMapper.readValue(anyString(), any(Class.class))).thenThrow(new JsonMappingException(jsonReponseString));
			jsonResponseParser.parseFeedServiceJsonResponse(jsonReponseString);
		}catch(RestResponseParsingException ex){
			assertTrue(ex.getMessage().contains("Error while parsing JSON response of REST service."));
		}catch(JsonMappingException je){
			fail("JsonMappingException should never be exposed outside.");
		}catch (final JsonParseException e) {
			fail("JsonParseException should never be exposed outside.");
		} catch (final IOException e) {
			fail("IOException should never be exposed outside.");
		}
	}
	
}
