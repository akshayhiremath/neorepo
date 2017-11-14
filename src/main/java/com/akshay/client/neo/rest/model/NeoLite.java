/**
 * 
 */
package com.akshay.client.neo.rest.model;

/**
 * @author AKSHAYH
 *
 */
public class NeoLite {

	private String id = null;
	private String name = null;
	private String date = null;
	
	private Double estimated_diameter_min_in_meters = null;
	private Double estimated_diameter_max_in_meters = null;
	
	private Double miss_distance_in_kilometers = null;
	private Double miss_distance_in_miles = null;
	
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
