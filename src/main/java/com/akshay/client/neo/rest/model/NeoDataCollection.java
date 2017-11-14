package com.akshay.client.neo.rest.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "links",
    "element_count",
    "near_earth_objects"
})

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class NeoDataCollection implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("links")
    private Links links;
    
    @JsonProperty("element_count")
    private Integer element_count;
    
    @JsonProperty("near_earth_objects")
    private ArrayList<ArrayList<NearEarthObject>> near_earth_objects;

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

	public Integer getElement_count() {
		return element_count;
	}

	public void setElement_count(Integer element_count) {
		this.element_count = element_count;
	}
	
	@JsonProperty("near_earth_objects")
	public ArrayList<ArrayList<NearEarthObject>> getNear_earth_objects() {
		return near_earth_objects;
	}
	@JsonProperty("near_earth_objects")
	public void setNear_earth_objects(ArrayList<ArrayList<NearEarthObject>> near_earth_objects) {
		this.near_earth_objects = near_earth_objects;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("links", links).append("elementCount", element_count).append("element_count", near_earth_objects).toString();
    }

}
