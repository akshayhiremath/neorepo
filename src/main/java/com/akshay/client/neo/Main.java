package com.akshay.client.neo;

import static com.akshay.client.neo.rest.utils.Constants.neoIntroduction;
import static com.akshay.client.neo.rest.utils.Constants.neoUsage;
import static com.akshay.client.neo.rest.utils.Constants.usageError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.akshay.client.neo.rest.exception.InputValidationException;
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
 * 
 * The class with main flow control of the Near Earth Object (NEO) data fetch utility.
 * <p>About:
	 *  <p>This is an utility to find some basic information about the Near Earth Objects (NEO).
	 *  This utility sources its data from REST service hosted by NASA.<br>
	 * 	The utility gives 3 points of information from the data in the specified date range:<br>
	 *	         1. Total no. of NEOs appeared in the input date range.<br>
     *           Details of:<br>
     *           2. Largest NEO appeared in the input date range,<br>
     *           3. Closest NEO appeared in the input date range,<br>
 	 *	As an input utility needs date range for which the user wants to query the data.<br>
	 *
 	 *	<p>Usage:<br>
     *           <tt>com.akshay.client.neo.Main START_DATE END_DATE<br>
     *   			e.g. com.akshay.client.neo.Main 2017-11-04 2017-11-11</tt>
     *
     *           <p>Argument date format is yyyy-MM-dd<br>
     *           Maximum period between START_DATE and END_DATE could be 7 days<br>
     *           	a. If only START_DATE is provided. Data will be fetched for 7 days from the START_DATE<br>
     *           	b. If no argument is provided. Data will be fetched for today's date<br>
 *   
 * <p>The application begins with execution of main method.<br>
 * The class contains other supporting methods such as isValid and printNeoDetails<br> 
 * to help the main execution with validation and console printing services.   
 * @author AKSHAYH
 *
 */
@Component
public class Main 
{
	private static final Logger logger = Logger.getLogger(Main.class);
	
	@Autowired
	private RestWebServiceClient restClient;
	@Autowired
	private ResponseParserUtil responseParser;
	@Autowired
	private NeoProcessor neoProcessor;

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
	
	/**
	 * <p>The main method of the application. The starting point.
	 * Here 3 main dependencies necessary to do the NEO processing are set,<br>
	 * 	a. RestClient The REST client,<br>
	 *  b. ResponseParser REST client response parser<br>
	 *  c. NeoProcessor  core processor to process the NEO object obtained from NASA service.<br>
	 *  
	 * @param args A string with application's input date or date range
	 */
	public static void main(String[] args)
    {   
		ApplicationContext neoContext = new ClassPathXmlApplicationContext("neo-applicationContext.xml");
		Main main = (Main)neoContext.getBean("main"); 
		String[] dateRange = args;
		try {
				main.execute(dateRange);
		}catch(InputValidationException e){
			System.err.println(usageError);
		}
		
    }
	
	public void execute(String[] dateRange) throws InputValidationException{
		
        System.out.println(neoIntroduction);
        
        System.out.println(neoUsage);
        
        System.out.println(
        		"============================================================================================================================================================\n");
        
		
		StringBuilder mainServiceUrl = new StringBuilder(Constants.NEO_SERVICE);
		//Preparing default query param (Authentication Key) needed by all services 
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add(Constants.FEED_SERVICE_PARAMETER_API_KEY, Constants.FEED_SERVICE_DEFAULT_API_KEY);
		
		//Validation of input arguments
		switch (dateRange.length) {
				case 1:
					if(isValid(dateRange)){
						mainServiceUrl.append(Constants.FEED_SERVICE_BY_DATE);
						queryParams.add(Constants.FEED_SERVICE_PARAMETER_START_DATE, dateRange[0]);
					}else{
						throw new InputValidationException(usageError);
					}
					break;
				case 2:
					if(isValid(dateRange)){
						mainServiceUrl.append(Constants.FEED_SERVICE_BY_DATE);
						queryParams.add(Constants.FEED_SERVICE_PARAMETER_START_DATE, dateRange[0]);
						queryParams.add(Constants.FEED_SERVICE_PARAMETER_END_DATE, dateRange[1]);
					}else{
						throw new InputValidationException(usageError);
					}
					break;
				default:
					mainServiceUrl.append(Constants.FEED_SERVICE_TODAY);
					break;
		}
		logger.debug("Input paramter validation completed.");
		
    	//Fetch the data from NASA site
		
        //Get Rest client
		restClient = getRestClient();
		
		String neoDataCollectionJsonString = null;
		try {
			//The REST call
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
		 
		NeoDataCollection neoDataCollection= null;
		try {
			//Parse the REST service response
			neoDataCollection = responseParser.parseFeedServiceJsonResponse(neoDataCollectionJsonString);
		} catch (RestResponseParsingException e2) {
			logger.error("Error while parsing the JSON response from REST service.");
		}

		// Process the data to find the largest neo and closest to the earth.
		
		//Initialize NEO processor used to process NEO raw data to get information of interest
		try {
			neoProcessor = getNeoProcessor();
			neoProcessor.initialize(neoDataCollection);
		} catch (NeoProcessorException e1) {
			logger.error("Error in NEO processing."+e1);
			return;
		}
		
		logger.info("Finding the largest NEO.");
		NeoLite largestNeo=null;
		try {
			//Use NEO processor to find largest NEO
			largestNeo = neoProcessor.findLargestNeo();
		} catch (NeoProcessorException e1) {
			logger.error("Error in NEO processing."+e1);
		}
		logger.info("Finding the closest NEO.");
		NeoLite closestNeo=null;
		try {
			//Use NEO processor to find closest NEO
			closestNeo = neoProcessor.findClosestNeo();
		} catch (NeoProcessorException e1) {
			logger.error("Error in NEO processing."+e1);
		}
		
		//Print total NEO count to console
		logger.info("TOTAL NO. OF NEOs is "+neoDataCollection.getElement_count());
		System.out.println(" TOTAL NO. OF NEOs is "
				+ neoDataCollection.getElement_count() + "\n");
		
		//Fetch Largest NEO details
		logger.info("Fetching the largest NEO details.");
		String largestNeoDetailsJsonString = null;
		try {
			//REST call to fetch the details of largest NEO
			largestNeoDetailsJsonString = (String) restClient.callRestWebService(
					Constants.NEO_SERVICE + Constants.NEO_BY_ID_SERVICE + largestNeo.getId(), queryParams, String.class);
		} catch (RestClientException e) {
			logger.error("REST Web Service call to fetch largest NEO details failed. "+e.getMessage());
		}
		
		try {
			//Parse REST response of largest NEO details 
			NearEarthObject neoLargest = getResponseParser().parseNeoByIdServiceJsonResponse(largestNeoDetailsJsonString);
			logger.info(
					"===========================================================LARGEST NEO DEAILS===========================================================");
			System.out.println(
					"===========================================================LARGEST NEO DEAILS===========================================================");
			//Print largest NEO details to console
			printNeoDetails(neoLargest);
			
		} catch (RestResponseParsingException e) {
			logger.error("Parsing of Detailed Neo object failed.");
		}
		
		//Fetch closest NEO details
		logger.info("Fetching the closest NEO details.");
		String closestNeoDetailsJsonString = null;
		try {
			//REST call to fetch the details of largest NEO
			closestNeoDetailsJsonString = (String) restClient.callRestWebService(
					Constants.NEO_SERVICE + Constants.NEO_BY_ID_SERVICE + closestNeo.getId(), queryParams, String.class);
		} catch (RestClientException e) {
			logger.error("REST Web Service call to fetch closest NEO details failed. "+e.getMessage());
		}
		
		try {
			//Parse REST response of closest NEO details
			NearEarthObject neoClosest = getResponseParser().parseNeoByIdServiceJsonResponse(closestNeoDetailsJsonString);
			logger.info(
					"===========================================================CLOSEST NEO DEAILS===========================================================");
			System.out.println(
					"===========================================================CLOSEST NEO DEAILS===========================================================");
			//Print closest NEO details to console
			printNeoDetails(neoClosest);
			
		} catch (RestResponseParsingException e) {
			logger.error("Parsing of Detailed Neo object failed.");
		}
	}
    
	/**
	 * <p>Method to check date format validity. Date is valid if,<br>
	 *  	a. following normal date conventions such as month value is in 1 to 12 and date is in 1 to 31<br>
	 *  	b. in yyyy-mm-dd format<br>
	 *  	b. in case of date range, the difference between START_DATE and END_DATE is less than or equal to 7 days 
	 * @param dateParams date as a String
	 * @return validation response true or false
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
    			if(days < 0 || days==0 || days > 7){
    				return false;
    			}
    		}	
    		
    	} catch (ParseException ex) {
    	    ex.printStackTrace();
    	}
    	return true;    	
    }
    
    /**
     * A support method to print the details of NEO. 
     * It has skeleton of print response which will be filled with values extracted from the NEO object passed as a parameter. 
     * @param neo a plain Near Earth Object POJO
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
