package com.akshay.client.neo.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "kilometers",
    "meters",
    "miles",
    "feet"
})
public class EstimatedDiameter implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -976112021620112079L;
	@JsonProperty("kilometers")
    private Kilometers kilometers;
    @JsonProperty("meters")
    private Meters meters;
    @JsonProperty("miles")
    private Miles miles;
    @JsonProperty("feet")
    private Feet feet;

    @JsonProperty("kilometers")
    public Kilometers getKilometers() {
        return kilometers;
    }

    @JsonProperty("kilometers")
    public void setKilometers(Kilometers kilometers) {
        this.kilometers = kilometers;
    }

    @JsonProperty("meters")
    public Meters getMeters() {
        return meters;
    }

    @JsonProperty("meters")
    public void setMeters(Meters meters) {
        this.meters = meters;
    }

    @JsonProperty("miles")
    public Miles getMiles() {
        return miles;
    }

    @JsonProperty("miles")
    public void setMiles(Miles miles) {
        this.miles = miles;
    }

    @JsonProperty("feet")
    public Feet getFeet() {
        return feet;
    }

    @JsonProperty("feet")
    public void setFeet(Feet feet) {
        this.feet = feet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("kilometers", kilometers).append("meters", meters).append("miles", miles).append("feet", feet).toString();
    }

}
