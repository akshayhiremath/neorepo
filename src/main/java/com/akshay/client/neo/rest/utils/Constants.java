package com.akshay.client.neo.rest.utils;

/**
 * 
 */
/**
 * @author AKSHAYH
 *
 */
public class Constants {

	public static final String NEO_SERVICE = "https://api.nasa.gov/neo";
	public static final String FEED_SERVICE_BY_DATE = "/rest/v1/feed";
	public static final String FEED_SERVICE_TODAY = "/rest/v1/feed/today";
	public static final String NEO_BY_ID_SERVICE = "/rest/v1/neo/";

	public static final String FEED_SERVICE_PARAMETER_DATE_FORMAT = "yyyy-MM-dd";
	public static final String FEED_SERVICE_PARAMETER_START_DATE = "start_date";// Format
																				// yyyy-MM-dd
	public static final String FEED_SERVICE_PARAMETER_END_DATE = "end_date";// Format
																			// yyyy-MM-dd
	public static final String FEED_SERVICE_PARAMETER_DETAILED = "detailed";
	public static final String FEED_SERVICE_PARAMETER_API_KEY = "api_key";

	public static final String FEED_SERVICE_DEFAULT_API_KEY = "DEMO_KEY";

	public static final String NEO_JSON_FIELD_NAME = "near_earth_objects";
}
