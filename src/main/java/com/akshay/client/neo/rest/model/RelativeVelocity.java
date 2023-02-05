
package com.akshay.client.neo.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "kilometers_per_second",
    "kilometers_per_hour",
    "miles_per_hour"
})
public class RelativeVelocity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 94518207386779060L;
	@JsonProperty("kilometers_per_second")
    private Double kilometers_per_second;
    @JsonProperty("kilometers_per_hour")
    private Double kilometers_per_hour;
    @JsonProperty("miles_per_hour")
    private Double miles_per_hour;

	public Double getKilometers_per_second() {
		return kilometers_per_second;
	}

	public void setKilometers_per_second(Double kilometers_per_second) {
		this.kilometers_per_second = kilometers_per_second;
	}

	public Double getKilometers_per_hour() {
		return kilometers_per_hour;
	}

	public void setKilometers_per_hour(Double kilometers_per_hour) {
		this.kilometers_per_hour = kilometers_per_hour;
	}

	public Double getMiles_per_hour() {
		return miles_per_hour;
	}

	public void setMiles_per_hour(Double miles_per_hour) {
		this.miles_per_hour = miles_per_hour;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this).append("kilometersPerSecond", kilometers_per_second).append("kilometersPerHour", kilometers_per_hour).append("milesPerHour", miles_per_hour).toString();
    }

}
