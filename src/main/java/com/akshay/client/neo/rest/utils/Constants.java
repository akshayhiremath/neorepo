package com.akshay.client.neo.rest.utils;

/**
 * 
 */
/**
 * Constants used in NEO utility.
 * @author AKSHAYH
 *
 */
public class Constants {

	/**
	 * An URL pointing to host and web path where NASA NEO REST service is hosted. 
	 */
	public static final String NEO_SERVICE = "https://api.nasa.gov/neo";
	/**
	 * Relative path to feed API of NASA NEO REST service to obtain by date feed 
	 */
	public static final String FEED_SERVICE_BY_DATE = "/rest/v1/feed";
	/**
	 * Relative path to feed API of NASA NEO REST service to obtain today's data 
	 */
	public static final String FEED_SERVICE_TODAY = "/rest/v1/feed/today";
	/*
	 * Relative path to individual NEO details API
	 */
	public static final String NEO_BY_ID_SERVICE = "/rest/v1/neo/";
	/**
	 * NEO feed service input date format.
	 */
	public static final String FEED_SERVICE_PARAMETER_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * NEO feed service input start_date query parameter name.
	 */
	public static final String FEED_SERVICE_PARAMETER_START_DATE = "start_date";// Format
																				// yyyy-MM-dd
	/**
	 * NEO feed service input end_date query parameter name.
	 */
	public static final String FEED_SERVICE_PARAMETER_END_DATE = "end_date";// Format
																			// yyyy-MM-dd
	
	/**
	 * NEO feed service query parameter indicating need of details.
	 */
	public static final String FEED_SERVICE_PARAMETER_DETAILED = "detailed";
	
	/**
	 * NEO REST service query parameter name for api_key.
	 */
	public static final String FEED_SERVICE_PARAMETER_API_KEY = "api_key";

	/**
	 * NEO REST service default API_KEY
	 */
	public static final String FEED_SERVICE_DEFAULT_API_KEY = "DEMO_KEY";

	/**
	 * Name of field in JSON response from NEO Rest service.
	 */
	public static final String NEO_JSON_FIELD_NAME = "near_earth_objects";
	
	/**
	 * String indicating the error in utility usage. This will be reported when input vaidation fails.
	 */
	public static final String usageError = " ERROR >>>>>>>>>>>>>>>>>>>>>>>>> Plz check input parameters.\n";
	
	/**
	 * NEO utility introduction message 
	 */
	public static final String neoIntroduction = "\n\n=========================================================== Near Earth Object Data Fetch Utility ===========================================================\n"+ 
				"This is an utility to find some basic information about the Near Earth Objects (NEO).\n This utility sources its data from REST service hosted by NASA "+
    		"\n The utility gives 3 points of information from the data in the specified date range:\n"+
    		"		1. Total no. of NEOs appeared in the input date range.\n"+
    		"		Details of: \n"+
    		"		2. Largest NEO appeared in the input date range,\n"+
    		"		3. Closest NEO appeared in the input date range,\n"+
    		" As an input utility needs date range for which the user wants to query the data.\n";
	
	/**
	 * NEO utility usage message 
	 */
	public static final String neoUsage = " Usage: \n"+
    		"   Using utility:\n"+
    		"	\tOn Windows: NeoUtility.bat <START_DATE> <END_DATE>\n"+
    		"	\tOn Linux: NeoUtility.sh <START_DATE> <END_DATE>\n"+
    		"	2. If running direct jar: "+"\n"+
    		"		java -Djava.net.useSystemProxies=true -jar neo-1.2.0-SNAPSHOT-jar-with-dependencies.jar <START_DATE> <END_DATE>\n"+
    		"	e.g. java -Djava.net.useSystemProxies=true -jar neo-1.2.0-SNAPSHOT-jar-with-dependencies.jar 2017-11-11 2017-11-18.\n\n"+
    		"		Argument date format is yyyy-MM-dd\n"+
    		"		Maximum period between START_DATE and END_DATE could be 7 days.\n"+	
    		"		a. If only START_DATE is provided. Data will be fetched for 7 days from the START_DATE.\n"+
    		"		b. If no argument is provided. Data will be fetched for today's date.\n";
	
}
