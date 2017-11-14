package com.akshay.client.neo.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "astronomical",
    "lunar",
    "kilometers",
    "miles"
})
public class MissDistance implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1973929688026132815L;
	@JsonProperty("astronomical")
    private Double astronomical;
    @JsonProperty("lunar")
    private Double lunar;
    @JsonProperty("kilometers")
    private Double kilometers;
    @JsonProperty("miles")
    private Double miles;

	public Double getAstronomical() {
		return astronomical;
	}

	public void setAstronomical(Double astronomical) {
		this.astronomical = astronomical;
	}

	public Double getLunar() {
		return lunar;
	}

	public void setLunar(Double lunar) {
		this.lunar = lunar;
	}

	public Double getKilometers() {
		return kilometers;
	}

	public void setKilometers(Double kilometers) {
		this.kilometers = kilometers;
	}

	public Double getMiles() {
		return miles;
	}

	public void setMiles(Double miles) {
		this.miles = miles;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this).append("astronomical", astronomical).append("lunar", lunar).append("kilometers", kilometers).append("miles", miles).toString();
    }

}
