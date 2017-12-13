/**
 * 
 */
package com.akshay.client.neo.rest.processor;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.akshay.client.neo.rest.exception.NeoProcessorException;
import com.akshay.client.neo.rest.model.CloseApproachDatum;
import com.akshay.client.neo.rest.model.EstimatedDiameter;
import com.akshay.client.neo.rest.model.Meters;
import com.akshay.client.neo.rest.model.MissDistance;
import com.akshay.client.neo.rest.model.NearEarthObject;
import com.akshay.client.neo.rest.model.NeoDataCollection;
import com.akshay.client.neo.rest.model.NeoLite;

/**
 * This is a core logic processor of the NEO utility. NeoProcessor is
 * initialized with the a collection of raw Neo data fetched form NASA service.
 * A raw Neo data collection is encapsulated in class
 * {@link com.akshay.client.neo.rest.model.NeoDataCollection}. In initialization
 * of NeoProcessor the raw Neo objects are converted to lighter Neo objects. The
 * lighter Neo object {@link com.akshay.client.neo.rest.model.NeoLite}. NeoLite
 * has only a limited number of attributes of Neo that are necessary to support
 * the operations exposed by NeoProcessor.
 * 
 * The processor supports two operations findLargestNeo and findClosestNeo.
 * 
 * @author AKSHAYH
 *
 */
public class NeoProcessor {

	private static Logger logger = Logger.getLogger(NeoProcessor.class);
	private NeoDataCollection neoDataCollection = null;
	private ArrayList<NeoLite> neoLiteList = null;

	public NeoProcessor() {
	}

	public NeoProcessor(final NeoDataCollection neoDataCollection) throws NeoProcessorException {
		this.neoDataCollection = neoDataCollection;
		this.neoLiteList = initialize();
	}

	public ArrayList<NeoLite> initialize() throws NeoProcessorException {
		return prepareNeoLiteList(this.neoDataCollection);
	}

	public NeoDataCollection getNeoDataCollection() {
		return neoDataCollection;
	}

	public void setNeoDataCollection(final NeoDataCollection neoDataCollection) {
		this.neoDataCollection = neoDataCollection;
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

		Double estimate_diameter_min = 0.0;
		Double estimate_diameter_max = 0.0;
		Double averageDiameter = 0.0;
		Double lastLargestAvgDiameter = 0.0;
		NeoLite largestNeo = null;
		for (NeoLite neoLite : getNeoLiteList()) {
			if (neoLite != null) {
				estimate_diameter_max = neoLite.getEstimated_diameter_max_in_meters();
				estimate_diameter_min = neoLite.getEstimated_diameter_min_in_meters();

				if (estimate_diameter_max != null && estimate_diameter_min != null) {
					averageDiameter = (estimate_diameter_max + estimate_diameter_min) / 2;
					if (averageDiameter > lastLargestAvgDiameter) {
						largestNeo = neoLite;
						lastLargestAvgDiameter = averageDiameter;
					}
				} else {
					logger.trace("Neo Id: " + neoLite.getId() + " -> estimated_diameter_max or min is null.");
				}
			} else {
				logger.trace("Null NeoLite instance found.");
			}
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

		Double lastLeastMissDistance = 9000000000.0;
		Double missDistanceInKms = 0.0;
		NeoLite closestNeo = null;
		for (NeoLite neoLite : getNeoLiteList()) {
			if (neoLite != null) {
				missDistanceInKms = neoLite.getMiss_distance_in_kilometers();

				if (missDistanceInKms > 0 && missDistanceInKms < lastLeastMissDistance) {
					lastLeastMissDistance = missDistanceInKms;
					closestNeo = neoLite;
				}
			} else {
				logger.trace("Null NeoLite instance found.");
			}
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

		ArrayList<ArrayList<NearEarthObject>> neoList = neoDataCollection.getNear_earth_objects();
		if (neoList == null) {
			logger.error(
					"Error while initializing the NeoProcessor. NEO list in the NeoDataCollection POJO is null.  Plz check JSON response parser output.");
			throw new NeoProcessorException("NEO list in the NeoDataCollection POJO is null.");
		}
		ArrayList<NeoLite> neoLiteListLocal = new ArrayList<>();
		NeoLite neoLite = null;
		EstimatedDiameter estimatedDiameter = null;
		Meters meters = null;

		ArrayList<CloseApproachDatum> cadList = null;
		CloseApproachDatum closeApproachData = null;
		MissDistance missDistance = null;
		logger.debug(
				"Iterating over the NEO list to gather necessary data to form NEO Lite objects' list with necessary information");
		for (ArrayList<NearEarthObject> neosOnDate : neoList) {
			// Iterate over the NEO list to gather necessary data to form NEO
			// Lite objects' list with necessary information
			for (NearEarthObject neo : neosOnDate) {
				neoLite = new NeoLite();
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

					neoLiteListLocal.add(neoLite);
				}
			}
		}

		return neoLiteListLocal;
	}

}
