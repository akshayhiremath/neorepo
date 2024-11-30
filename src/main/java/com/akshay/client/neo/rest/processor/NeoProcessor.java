/**
 * 
 */
package com.akshay.client.neo.rest.processor;

import java.util.ArrayList;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.akshay.client.neo.rest.exception.NeoProcessorException;
import com.akshay.client.neo.rest.model.CloseApproachDatum;
import com.akshay.client.neo.rest.model.EstimatedDiameter;
import com.akshay.client.neo.rest.model.Meters;
import com.akshay.client.neo.rest.model.MissDistance;
import com.akshay.client.neo.rest.model.NearEarthObject;
import com.akshay.client.neo.rest.model.NeoDataCollection;
import com.akshay.client.neo.rest.model.NeoLite;

/**
 * This is a core logic processor of the NEO utility. NeoProcessor needs to be
 * initialized with the a collection of raw Neo data collection
 * {@link com.akshay.client.neo.rest.model.NeoDataCollection} Raw Neo data
 * collection could be passed to the constructor at the time of instantiation or
 * it could be passed to of NeoProcessor by separate call to initialize. The
 * later option is useful when NeoDataCollection is being generated dynamically
 * and is not ready at the time instantiating the NeoProcessor. The Neo utility is
 * passing a raw Neo data collection dynamically fetched from NASA service.
 * In the initialization, the raw Neo objects are converted to lighter Neo objects. 
 * The lighter Neo object is {@link com.akshay.client.neo.rest.model.NeoLite}.
 *  NeoLite has only a limited number of attributes of Neo that are necessary to support the
 * operations exposed by NeoProcessor.
 * 
 * The processor supports two operations findLargestNeo and findClosestNeo.
 * 
 * @author AKSHAYH
 *
 */
@Component
public class NeoProcessor{

	private static Logger logger = LogManager.getLogger(NeoProcessor.class);
	private ArrayList<NeoLite> neoLiteList;

	public NeoProcessor() {
	}

	public NeoProcessor(final NeoDataCollection neoDataCollection) throws NeoProcessorException {
			initialize(neoDataCollection);
	}

	public void initialize(final NeoDataCollection neoDataCollection) throws NeoProcessorException {
			setNeoLiteList(prepareNeoLiteList(neoDataCollection));
	}

	public ArrayList<NeoLite> getNeoLiteList() {
		return neoLiteList;
	}

	public void setNeoLiteList(ArrayList<NeoLite> neoLiteList) {
		this.neoLiteList = neoLiteList;
	}

	/**
	 * Finds largest Neo object out of the collection of Neo objects for which
	 * NeoProcessor is initialized.
	 * 
	 * @return the largest Neo object
	 * @throws NeoProcessorException
	 *             exception to report issue in NeoLite data. If this occurs check
	 *             the NeoLite data generated in NeoProcessor initialization.
	 */
	public NeoLite findLargestNeo() throws NeoProcessorException {
		
		NeoLite largestNeo = null;
		try {
			Comparator<NeoLite> comp = (n1, n2) -> Double.compare(
					(n1.getEstimated_diameter_max_in_meters() + n1.getEstimated_diameter_min_in_meters()) / 2,
					(n2.getEstimated_diameter_max_in_meters() + n2.getEstimated_diameter_min_in_meters()) / 2);
			largestNeo = getNeoLiteList().stream().max(comp).get();

		} catch (NullPointerException npe) {
			logger.trace("Null NeoLite instance found in NeoLite list OR estimated_diameter_max or min is null.");
			throw new NeoProcessorException("Null NeoLite instance found in NeoLite list.");
		}

		if (largestNeo == null) {
			throw new NeoProcessorException("Largest Neo finding failed. Problem with data in NeoLite list.");
		}

		return largestNeo;
	}

	/**
	 * Finds closest Neo object out of the collection of Neo objects for which
	 * NeoProcessor is initialized.
	 * 
	 * @return the largest Neo object
	 * @throws NeoProcessorException
	 *             exception to report issue in NeoLite data. If this occurs check
	 *             the NeoLite data generated in NeoProcessor initialization.
	 */
	public NeoLite findClosestNeo() throws NeoProcessorException {
		
		NeoLite closestNeo = null;
		try {
			final Comparator<NeoLite> comp = (n1, n2) -> Double.compare(n1.getMiss_distance_in_kilometers(),
					n2.getMiss_distance_in_kilometers());
			closestNeo = getNeoLiteList().stream().min(comp).get();
		} catch (NullPointerException npe) {
			logger.trace("Null NeoLite instance found.");
			throw new NeoProcessorException("Null NeoLite instance found in NeoLite list.");
		}

		if (closestNeo == null) {
			throw new NeoProcessorException("Largest Neo finding failed. Problem with data in NeoLite list.");
		}
		return closestNeo;
	}

	/**
	 * This method prepares a list of stripped down and simplified version of raw
	 * Neo data object. A raw Neo data object obtained from REST service response
	 * has a lot of attributes that are unnecessary in processing logic of
	 * operations in NeoProcessor. Also the raw Neo data object structure is complex
	 * to iterate over and process a collection to get results. Here the
	 * NeoDataCollection is processed to obtain a list of {@link com.akshay.client.neo.rest.model.NeoLite}
	 * 
	 * @param neoDataCollection
	 *            a raw Neo data collection
	 * @return a list of {@link com.akshay.client.neo.rest.model.NeoLite}, a refined
	 *         Neo data collection.
	 * @throws NeoProcessorException exception to report issue in NeoLite data. If this occurs check
	 *             					the NeoLite data generated in NeoProcessor initialization.
	 */
	private ArrayList<NeoLite> prepareNeoLiteList(final NeoDataCollection neoDataCollection)
			throws NeoProcessorException {

		if (neoDataCollection == null) {
			logger.error(
					"Error while initializing the NeoProcessor. NEO list in the NeoDataCollection POJO is null.  Plz check JSON response parser output.");
			throw new NeoProcessorException("The NeoDataCollection POJO is null.");
		}

		ArrayList<ArrayList<NearEarthObject>> neoListBundle  = neoDataCollection.getNear_earth_objects();
		if (neoListBundle == null) {
			logger.error(
					"Error while initializing the NeoProcessor. NEO list in the NeoDataCollection POJO is null.  Plz check JSON response parser output.");
			throw new NeoProcessorException("NEO list in the NeoDataCollection POJO is null.");
		}
		ArrayList<NeoLite> neoLiteListLocal = new ArrayList<>();
		logger.debug(
				"Iterating over the NEO list to gather necessary data to form NEO Lite objects' list with necessary information");
			// Iterate over the NEO list to gather necessary data to form NEO
			// Lite objects' list with necessary information
			neoListBundle.stream().flatMap(neoList -> neoList.stream()).forEach(neo -> transformRawNeo(neo,neoLiteListLocal));

		return neoLiteListLocal;
	}
	
	/**
	 * Method to transform individual raw Neo to ligher Neo
	 * @param neo Raw Neo object to transform
	 * @param neoLiteList list reference to collect transformed lighter Neo 
	 */
	private static void transformRawNeo(NearEarthObject neo, ArrayList<NeoLite> neoLiteList) {
		ArrayList<CloseApproachDatum> cadList = null;
		CloseApproachDatum closeApproachData = null;
		MissDistance missDistance = null;
		EstimatedDiameter estimatedDiameter = null;
		Meters meters = null;
		
		NeoLite neoLite = new NeoLite();
		if (neo != null) {
			neoLite.setId((neo.getNeo_reference_id()));
			neoLite.setName(neo.getName());
			estimatedDiameter = neo.getEstimated_diameter();
			if (estimatedDiameter != null) {
				meters = estimatedDiameter.getMeters();
				if (meters != null) {
					neoLite.setEstimated_diameter_min_in_meters(meters.getEstimatedDiameterMin());
					neoLite.setEstimated_diameter_max_in_meters(meters.getEstimatedDiameterMax());
				} else {
					logger.trace("Neo Id: " + neo.getNeo_reference_id() + " -> meters is null.");
				}
			} else {
				logger.trace("Neo Id: " + neo.getNeo_reference_id() + " -> estimated_diameter is null.");
			}
			cadList = neo.getClose_approach_data();
			if (cadList != null) {
				closeApproachData = cadList.get(0);
				if (closeApproachData != null) {
					neoLite.setDate(closeApproachData.getClose_approach_date());
					missDistance = closeApproachData.getMiss_distance();
				} else {
					logger.trace("Neo Id: " + neo.getNeo_reference_id() + " -> close_approach_data is null.");
				}
				if (missDistance != null) {
					neoLite.setMiss_distance_in_kilometers(missDistance.getKilometers());
					neoLite.setMiss_distance_in_miles(missDistance.getMiles());
				} else {
					logger.trace("Neo Id: " + neo.getNeo_reference_id() + " -> miss_distance is null.");
				}
			}
		}
		
		neoLiteList.add(neoLite);
	}

}
