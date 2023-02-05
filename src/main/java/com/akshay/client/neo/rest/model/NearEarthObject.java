package com.akshay.client.neo.rest.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * A raw Near Earth Object with all the details as obtained from the NASA service response.
 * This has numerous fields. Each field name is self-explanatory.
 * {@link com.akshay.client.neo.rest.model.NeoDataCollection} is a collection of NEOs in this format plus some metadata. 
 * @author AKSHAYH
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	    "links",
	    "neo_reference_id",
	    "name",
	    "nasa_jpl_url",
	    "absolute_magnitude_h",
	    "estimated_diameter",
	    "is_potentially_hazardous_asteroid",
	    "close_approach_data",
	    "orbital_data"
})
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class NearEarthObject implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4803486383506304194L;
		@JsonProperty("links")
	    private Link links;
	    @JsonProperty("neo_reference_id")
	    private String neo_reference_id;
	    @JsonProperty("name")
	    private String name;
	    @JsonProperty("nasa_jpl_url")
	    private String nasa_jpl_url;
	    @JsonProperty("absolute_magnitude_h")
	    private Double absolute_magnitude_h;
	    @JsonProperty("estimated_diameter")
	    private EstimatedDiameter estimated_diameter;
	    @JsonProperty("is_potentially_hazardous_asteroid")
	    private Boolean is_potentially_hazardous_asteroid;

	    private ArrayList<CloseApproachDatum> close_approach_data = null;

	    private OrbitalData orbital_data;

	    public Link getLinks() {
	        return links;
	    }

	    public void setLinks(Link links) {
	        this.links = links;
	    }
	    
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getNeo_reference_id() {
			return neo_reference_id;
		}

		public void setNeo_reference_id(String neo_reference_id) {
			this.neo_reference_id = neo_reference_id;
		}

		public String getNasa_jpl_url() {
			return nasa_jpl_url;
		}

		public void setNasa_jpl_url(String nasa_jpl_url) {
			this.nasa_jpl_url = nasa_jpl_url;
		}

		public Double getAbsolute_magnitude_h() {
			return absolute_magnitude_h;
		}

		public void setAbsolute_magnitude_h(Double absolute_magnitude_h) {
			this.absolute_magnitude_h = absolute_magnitude_h;
		}

		public EstimatedDiameter getEstimated_diameter() {
			return estimated_diameter;
		}

		public void setEstimated_diameter(EstimatedDiameter estimated_diameter) {
			this.estimated_diameter = estimated_diameter;
		}

		public Boolean getIs_potentially_hazardous_asteroid() {
			return is_potentially_hazardous_asteroid;
		}

		public void setIs_potentially_hazardous_asteroid(Boolean is_potentially_hazardous_asteroid) {
			this.is_potentially_hazardous_asteroid = is_potentially_hazardous_asteroid;
		}

		public ArrayList<CloseApproachDatum> getClose_approach_data() {
			return close_approach_data;
		}

		public void setClose_approach_data(ArrayList<CloseApproachDatum> close_approach_data) {
			this.close_approach_data = close_approach_data;
		}

		public OrbitalData getOrbital_data() {
			return orbital_data;
		}

		public void setOrbital_data(OrbitalData orbital_data) {
			this.orbital_data = orbital_data;
		}

		@Override
	    public String toString() {
	        return new ToStringBuilder(this).append("links", links).append("\n")
				.append("neoReferenceId", neo_reference_id).append("\n")
				.append("name", name).append("\n")
				.append("nasaJplUrl", nasa_jpl_url).append("\n")
				.append("absoluteMagnitudeH", absolute_magnitude_h).append("estimatedDiameter", estimated_diameter).append("\n")
				.append("isPotentiallyHazardousAsteroid", is_potentially_hazardous_asteroid).append("\n")
				.append("closeApproachData", close_approach_data)
				.append("orbitalData", orbital_data).toString();
	    }

}
