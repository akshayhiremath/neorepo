/**
 * 
 */
package com.akshay.client.neo.rest.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.akshay.client.neo.rest.exception.RestResponseParsingException;
import com.akshay.client.neo.rest.model.NearEarthObject;
import com.akshay.client.neo.rest.model.NeoDataCollection;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author AKSHAYH
 *
 */
public class ResponseParserUtil {

	private static Logger logger = Logger.getLogger(ResponseParserUtil.class);
	private ObjectMapper jacksonMapper = null;
	private JsonParser gsonParser = null;

	public ResponseParserUtil() {
		jacksonMapper = new ObjectMapper();
		jacksonMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		jacksonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		gsonParser = new JsonParser();
	}

	public ObjectMapper getJacksonMapper() {
		return jacksonMapper;
	}

	public void setJacksonMapper(ObjectMapper jacksonMapper) {
		this.jacksonMapper = jacksonMapper;
	}

	public JsonParser getGsonParser() {
		return gsonParser;
	}

	public void setGsonParser(JsonParser gsonParser) {
		this.gsonParser = gsonParser;
	}

	public NeoDataCollection parseFeedServiceJsonResponse(final String jsonResponse)
			throws RestResponseParsingException {

		NeoDataCollection neoDataCollection = null;
		try {
			neoDataCollection = getJacksonMapper().readValue(jsonResponse, NeoDataCollection.class);
		} catch (final JsonParseException e) {
			logger.error("Error while parsing JSON. Exception: " + e);
			throw new RestResponseParsingException("Error while parsing JSON response of REST service.: " + e);
		} catch (final JsonMappingException e) {
			logger.error("Error while parsing JSON. Exception: " + e);
			throw new RestResponseParsingException("Error while parsing JSON response of REST service.: " + e);
		} catch (final IOException e) {
			logger.error("Error while parsing JSON. Exception: " + e);
			throw new RestResponseParsingException("Error while parsing JSON response of REST service.: " + e);
		}

		JsonElement jsonElement = getGsonParser().parse(jsonResponse);
		JsonObject neoDataCollectionJson = null;
		if (jsonElement != null) {
			neoDataCollectionJson = jsonElement.getAsJsonObject();
		} else {
			logger.error("JSON parsing failed.");
		}
		JsonObject dateWiseNeoCollection = null;
		String dateWiseNeosJson = null;
		// Take out the date wise set of collections of Near Earth Objects
		if (neoDataCollectionJson.get(Constants.NEO_JSON_FIELD_NAME) != null) {
			dateWiseNeosJson = neoDataCollectionJson.get(Constants.NEO_JSON_FIELD_NAME).toString();
		}

		JsonParser gsonParser = getGsonParser();
		if (gsonParser.parse(dateWiseNeosJson) != null) {
			dateWiseNeoCollection = gsonParser.parse(dateWiseNeosJson).getAsJsonObject();
		}

		ArrayList<NearEarthObject> neoList = new ArrayList<>();
		ArrayList<ArrayList<NearEarthObject>> near_earth_objects = neoDataCollection.getNear_earth_objects();
		near_earth_objects.clear();
		for (Map.Entry<String, JsonElement> neosOnDate : dateWiseNeoCollection.entrySet()) {// Taking
																							// the
																							// set
																							// of
																							// collections
																							// of
																							// NEOs
																							// for
																							// each
																							// date
			JsonElement neoSetForTheDate = neosOnDate.getValue();
			JsonArray neoArray = null;
			if (neoSetForTheDate.isJsonArray()) {
				neoArray = neoSetForTheDate.getAsJsonArray();
			}
			NearEarthObject neo = null;
			for (JsonElement neoString : neoArray) {// Fetching and parsing the
													// actual NEOs
				try {
					neo = getJacksonMapper().readValue(neoString.toString(), NearEarthObject.class);
					neoList.add(neo);
				} catch (JsonParseException e) {
					logger.error("Error while parsing JSON. Exception: " + e);
					throw new RestResponseParsingException("Error while parsing JSON response of REST service.: " + e);
				} catch (JsonMappingException e) {
					logger.error("Error while parsing JSON. Exception: " + e);
					throw new RestResponseParsingException("Error while parsing JSON response of REST service.: " + e);
				} catch (IOException e) {
					logger.error("Error while parsing JSON. Exception: " + e);
					throw new RestResponseParsingException("Error while parsing JSON response of REST service.: " + e);
				}
			}

			near_earth_objects.add(neoList);

		}
		return neoDataCollection;
	}

	public NearEarthObject parseNeoByIdServiceJsonResponse(final String jsonResponse)
			throws RestResponseParsingException {
		NearEarthObject neo = null;
		try {
			neo = getJacksonMapper().readValue(jsonResponse, NearEarthObject.class);
		} catch (IOException e) {
			logger.error("Error while parsing the JSON to NearEarthObject.");
			throw new RestResponseParsingException("Error while parsing JSON response of REST service.: " + e);
		}
		return neo;

	}

	public String pojoToJson(final Object object) {

		String jsonString = null;

		try {
			jsonString = getJacksonMapper().writeValueAsString(object);
		} catch (IOException e) {
			logger.error("Error while converting POJO to JSON. Exception: " + e);
		}

		return jsonString;
	}

}
