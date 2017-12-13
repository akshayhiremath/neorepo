/**
 * 
 */
package com.akshay.client.neo.rest.model;

/**
 * A lighter, simplified version of NEO POJO for processing and obtaining information of interest.
 * This has lesser attributes compared to {@link com.akshay.client.neo.rest.model.NeoDataCollection} 
 * and structured to efficiently process in {@link com.akshay.client.neo.rest.processor.NeoProcessor}
 * This class contains only fields necessary to find largest and closest NEO.
 *  
 * @author AKSHAYH
 *
 */
public class NeoLite {
	/**
	 * neo_referene_id of the NEODataCollection (a raw NEO data collection) 
	 */
	private String id = null;
	/**
	 * Name of the NEO obtained from NEODataCollection (a raw NEO data collection)
	 */
	private String name = null;
	/**
	 * Close approach date of this NEO
	 */
	private String date = null;
	
	/**
	 * Minimum estimated diameter in meters 
	 */
	private Double estimated_diameter_min_in_meters = null;
	
	/**
	 * Maximum estimated diameter in meters 
	 */
	private Double estimated_diameter_max_in_meters = null;
	
	/**
	 * Miss distance in kilometers 
	 */
	private Double miss_distance_in_kilometers = null;
	/**
	 * Miss distance in miles 
	 */
	private Double miss_distance_in_miles = null;
	
	/**
	 * Orbital data obtained from NEODataCollection {@link com.akshay.client.neo.rest.model.OrbitalData}
	 */
	private OrbitalData orbital_data = null;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getEstimated_diameter_min_in_meters() {
		return estimated_diameter_min_in_meters;
	}
	public void setEstimated_diameter_min_in_meters(Double estimated_diameter_min_in_meters) {
		this.estimated_diameter_min_in_meters = estimated_diameter_min_in_meters;
	}
	public Double getEstimated_diameter_max_in_meters() {
		return estimated_diameter_max_in_meters;
	}
	public void setEstimated_diameter_max_in_meters(Double estimated_diameter_max_in_meters) {
		this.estimated_diameter_max_in_meters = estimated_diameter_max_in_meters;
	}
	public Double getMiss_distance_in_kilometers() {
		return miss_distance_in_kilometers;
	}
	public void setMiss_distance_in_kilometers(Double miss_distance_in_kilometers) {
		this.miss_distance_in_kilometers = miss_distance_in_kilometers;
	}
	public Double getMiss_distance_in_miles() {
		return miss_distance_in_miles;
	}
	public void setMiss_distance_in_miles(Double miss_distance_in_miles) {
		this.miss_distance_in_miles = miss_distance_in_miles;
	}
	public OrbitalData getOrbital_data() {
		return orbital_data;
	}
	public void setOrbital_data(OrbitalData orbital_data) {
		this.orbital_data = orbital_data;
	}
	
	
}
