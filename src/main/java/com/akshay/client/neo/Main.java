package com.akshay.client.neo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.akshay.client.neo.rest.exception.NeoProcessorException;
import com.akshay.client.neo.rest.exception.RestClientException;
import com.akshay.client.neo.rest.exception.RestResponseParsingException;
import com.akshay.client.neo.rest.model.NearEarthObject;
import com.akshay.client.neo.rest.model.NeoDataCollection;
import com.akshay.client.neo.rest.model.NeoLite;
import com.akshay.client.neo.rest.processor.NeoProcessor;
import com.akshay.client.neo.rest.service.RestWebServiceClient;
import com.akshay.client.neo.rest.utils.Constants;
import com.akshay.client.neo.rest.utils.ResponseParserUtil;
import com.sun.jersey.core.util.MultivaluedMapImpl;


/**
 * Near Earth Object NEO data fetch utility
 * @author AKSHAYH
 *
 */
public class Main 
{
	private static final Logger logger = Logger.getLogger(Main.class);
	
	
	private RestWebServiceClient restClient = null;
	private ResponseParserUtil responseParser = null;
	private NeoDataCollection neoDataCollection = null;
	private NeoProcessor neoProcessor = null;
	private static String usageError = " ERROR >>>>>>>>>>>>>>>>>>>>>>>>> Plz check input parameters.\n";

	public RestWebServiceClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestWebServiceClient restClient) {
		this.restClient = restClient;
	}

	public ResponseParserUtil getResponseParser() {
		return responseParser;
	}

	public void setResponseParser(ResponseParserUtil responseParser) {
		this.responseParser = responseParser;
	}

	public NeoProcessor getNeoProcessor() {
		return neoProcessor;
	}

	public void setNeoProcessor(NeoProcessor neoProcessor) {
		this.neoProcessor = neoProcessor;
	}

	public NeoDataCollection getNeoDataCollection() {
		return neoDataCollection;
	}

	public void setNeoDataCollection(NeoDataCollection neoDataCollection) {
		this.neoDataCollection = neoDataCollection;
	}

	public static void main(String[] args)
    {   
		Main mainInstance = new Main();
		
		String[] dateRange = args;
		mainInstance.setRestClient(new RestWebServiceClient());
		mainInstance.setResponseParser(new ResponseParserUtil());
		mainInstance.setNeoProcessor(new NeoProcessor());
		
		mainInstance.execute(dateRange);
		
    }
	
	public void execute(String[] dateRange){
		System.out.println(
				"\n\n=========================================================== Near Earth Object Data Fetch Utility ===========================================================\n");
        System.out.println(" This is an utility to find some basic information about the Near Earth Objects (NEO).\n This utility sources its data from REST service hosted by NASA "+
        		"\n The utility gives 3 points of information from the data in the specified date range:\n"+
        		"		1. Total no. of NEOs appeared in the input date range.\n"+
        		"		Details of: \n"+
        		"		2. Largest NEO appeared in the input date range,\n"+
        		"		3. Closest NEO appeared in the input date range,\n"+
        		" As an input utility needs date range for which the user wants to query the data.");
        String usage = " Usage: \n"+
        		"   1. If using .bat: NeoUtility.bat <START_DATE> <END_DATE>\n"+
        		"	2. If running direct java class: "+"\n"+
        		"		com.akshay.client.neo.Main <START_DATE> <END_DATE>\n"+
        		"	e.g. com.akshay.client.neo.Main 2017-11-04 2017-11-11.\n\n"+	
        		"		Argument date format is yyyy-MM-dd\n"+
        		"		Maximum period between START_DATE and END_DATE could be 7 days.\n"+	
        		"		a. If only START_DATE is provided. Data will be fetched for 7 days from the START_DATE.\n"+
        		"		b. If no argument is provided. Data will be fetched for today's date.\n"+
        		"	Make sure all the libraries(jars in lib dir + neo-1.0.0-SNAPSHOT.jar) are in classpath.";
        
        System.out.println("\n"+usage);
        
        System.out.println(
        		"============================================================================================================================================================\n");
        
		
		StringBuilder mainServiceUrl = new StringBuilder(Constants.NEO_SERVICE);
		//Preparing default query param (Authentication Key) needed by all services 
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add(Constants.FEED_SERVICE_PARAMETER_API_KEY, Constants.FEED_SERVICE_DEFAULT_API_KEY);
		
		switch (dateRange.length) {
				case 1:
					if(isValid(dateRange)){
						mainServiceUrl.append(Constants.FEED_SERVICE_BY_DATE);
						queryParams.add(Constants.FEED_SERVICE_PARAMETER_START_DATE, dateRange[0]);
					}else{
						System.err.println(usageError+"\n"+usage);
						return;
					}
					break;
				case 2:
					if(isValid(dateRange)){
						mainServiceUrl.append(Constants.FEED_SERVICE_BY_DATE);
						queryParams.add(Constants.FEED_SERVICE_PARAMETER_START_DATE, dateRange[0]);
						queryParams.add(Constants.FEED_SERVICE_PARAMETER_END_DATE, dateRange[1]);
					}else{
						System.err.println(usageError+"\n"+usage);
						return;
					}
					break;
				default:
					mainServiceUrl.append(Constants.FEED_SERVICE_TODAY);
					break;
		}
		logger.debug("Input paramter validation completed.");
		
    	//Fetch the data from NASA site
        //Rest client
		restClient = getRestClient();
		
		String neoDataCollectionJsonString = null;
		try {
			neoDataCollectionJsonString = (String) restClient
					.callRestWebService(mainServiceUrl.toString(), queryParams, String.class);
		} catch (RestClientException e) {
			logger.error("REST Web Service call to fetch NEOs for the date failed. "+e.getMessage());
			System.err.println("REST Web Service call to fetch NEOs for the date failed. "+e.getMessage());
			return;
		}
		responseParser = getResponseParser();
		logger.debug("REST call to the service is done.");
		logger.debug("Parsing the JSON response of Feed Service.");
		 
		NeoDataCollection neoDataCollection= null;;
		try {
			neoDataCollection = responseParser.parseFeedServiceJsonResponse(neoDataCollectionJsonString);
		} catch (RestResponseParsingException e2) {
			logger.error("Error while parsing the JSON response from REST service.");
		}
		
		this.setNeoDataCollection(neoDataCollection);

		// Process the data to find the largest neo and closest to the earth.
		try {
			neoProcessor = getNeoProcessor();
			neoProcessor.setNeoDataCollection(neoDataCollection);
			neoProcessor.setNeoLiteList(neoProcessor.initialize());
		} catch (NeoProcessorException e1) {
			logger.error("Error in NEO processing."+e1);
			return;
		}
		neoProcessor.setNeoDataCollection(neoDataCollection);
		
		logger.info("Finding the largest NEO.");
		NeoLite largestNeo=null;
		try {
			largestNeo = neoProcessor.findLargestNeo();
		} catch (NeoProcessorException e1) {
			logger.error("Error in NEO processing."+e1);
		}
		logger.info("Finding the closest NEO.");
		NeoLite closestNeo=null;
		try {
			closestNeo = neoProcessor.findClosestNeo();
		} catch (NeoProcessorException e1) {
			logger.error("Error in NEO processing."+e1);
		}
		logger.info("TOTAL NO. OF NEOs is "+neoDataCollection.getElement_count());
		System.out.println(" TOTAL NO. OF NEOs is "
				+ neoDataCollection.getElement_count() + "\n");
		
		logger.info("Fetching the largest NEO details.");
		String largestNeoDetailsJsonString = null;
		try {
			largestNeoDetailsJsonString = (String) restClient.callRestWebService(
					Constants.NEO_SERVICE + Constants.NEO_BY_ID_SERVICE + largestNeo.getId(), queryParams, String.class);
		} catch (RestClientException e) {
			logger.error("REST Web Service call to fetch largest NEO details failed. "+e.getMessage());
		}
		
		try {
			NearEarthObject neoLargest = getResponseParser().parseNeoByIdServiceJsonResponse(largestNeoDetailsJsonString);
			logger.info(
					"===========================================================LARGEST NEO DEAILS===========================================================");
			System.out.println(
					"===========================================================LARGEST NEO DEAILS===========================================================");
			printNeoDetails(neoLargest);
			
		} catch (RestResponseParsingException e) {
			logger.error("Parsing of Detailed Neo object failed.");
		}
		
		logger.info("Fetching the closest NEO details.");
		String closestNeoDetailsJsonString = null;
		try {
			closestNeoDetailsJsonString = (String) restClient.callRestWebService(
					Constants.NEO_SERVICE + Constants.NEO_BY_ID_SERVICE + closestNeo.getId(), queryParams, String.class);
		} catch (RestClientException e) {
			logger.error("REST Web Service call to fetch closest NEO details failed. "+e.getMessage());
		}
		
		try {
			NearEarthObject neoClosest = getResponseParser().parseNeoByIdServiceJsonResponse(closestNeoDetailsJsonString);
			logger.info(
					"===========================================================CLOSEST NEO DEAILS===========================================================");
			System.out.println(
					"===========================================================CLOSEST NEO DEAILS===========================================================");
			printNeoDetails(neoClosest);
			
		} catch (RestResponseParsingException e) {
			logger.error("Parsing of Detailed Neo object failed.");
		}
	}
    
	/**
	 * Method to check date format validity
	 * @param dateParam date as a String
	 * @return
	 */
    private static boolean isValid(String[] dateParams){
    	
    	Date date = null;
    	ArrayList<Date> dates = new ArrayList<>();
    	try {
    		for(String dateParam:dateParams){
	    	    SimpleDateFormat sdf = new SimpleDateFormat(Constants.FEED_SERVICE_PARAMETER_DATE_FORMAT);
	    	    date = sdf.parse(dateParam);
	    	    if (!dateParam.equals(sdf.format(date))) {
	    	        date = null;
	    	        return false;
	    	    }else{
	    	    	dates.add(date);
	    	    }
    		}
    		
    		if(dates.size()==2 && dates.get(0)!=null && dates.get(1)!=null){
    			long duration = dates.get(1).getTime() - dates.get(0).getTime();
    			long days = TimeUnit.MILLISECONDS.toDays(duration);
    			if(days > 7){
    				return false;
    			}
    		}	
    		
    	} catch (ParseException ex) {
    	    ex.printStackTrace();
    	}
    	return true;    	
    }
    
    /**
     * Method to print the details of Neo
     * @param neo - a plain Near Earth Object
     */
    private static void printNeoDetails(NearEarthObject neo){
    	
    	
    	int closeApproachCount = neo.getClose_approach_data().size();
		int futuremostNeoIndex = closeApproachCount - 1;
    	
    	System.out.println("In Brief:"+"\n"+
				"	Reference Id: "+neo.getNeo_reference_id()+"\n"+
				"	Name: "+neo.getName()+"\n"+
				"	Link: "+neo.getLinks()+"\n"+
				"	Absolute Magnitude: "+neo.getAbsolute_magnitude_h()+"\n"+
				"	Estimated Diameter Max (In Kms): "+neo.getEstimated_diameter().getKilometers().getEstimatedDiameterMax()+"\n"+
				"	Estimated Diameter Min (In Kms): "+neo.getEstimated_diameter().getKilometers().getEstimatedDiameterMin()+"\n"+
				"	Is potentially hazardous: "+neo.getIs_potentially_hazardous_asteroid()+"\n"+
				"	Close Approach Data: \n"+
				"		Close Approach Data Point Count: "+closeApproachCount+"\n"+
				" 		a. Earliest close approach date: "+neo.getClose_approach_data().get(0).getClose_approach_date()+"\n"+
				" 			i. Relative Velocity (In Km/sec): "+neo.getClose_approach_data().get(0).getRelative_velocity().getKilometers_per_second()+"\n"+
				" 			ii. Miss Distance (In Km): "+neo.getClose_approach_data().get(0).getMiss_distance().getKilometers()+"\n"+
				"			iii. Orbiting Body: "+neo.getClose_approach_data().get(0).getOrbiting_body()+"\n"+
				"		b. Anticipated furturemost close approach date: "+neo.getClose_approach_data().get(futuremostNeoIndex).getClose_approach_date()+"\n"+
				" 			i. Relative Velocity (In Km/sec): "+neo.getClose_approach_data().get(futuremostNeoIndex).getRelative_velocity().getKilometers_per_second()+"\n"+
				" 			ii. Miss Distance (In Km): "+neo.getClose_approach_data().get(futuremostNeoIndex).getMiss_distance().getKilometers()+"\n"+
				"			iii. Obiting Body: "+neo.getClose_approach_data().get(futuremostNeoIndex).getOrbiting_body()+"\n\n"+
				"	Orbital Data: "+neo.getOrbital_data().toString()
				);
    }
}
