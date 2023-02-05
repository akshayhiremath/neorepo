package com.akshay.client.neo.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "estimated_diameter_min",
    "estimated_diameter_max"
})
public class Feet implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2056765108340821870L;
	@JsonProperty("estimated_diameter_min")
    private Double estimatedDiameterMin;
    @JsonProperty("estimated_diameter_max")
    private Double estimatedDiameterMax;

    @JsonProperty("estimated_diameter_min")
    public Double getEstimatedDiameterMin() {
        return estimatedDiameterMin;
    }

    @JsonProperty("estimated_diameter_min")
    public void setEstimatedDiameterMin(Double estimatedDiameterMin) {
        this.estimatedDiameterMin = estimatedDiameterMin;
    }

    @JsonProperty("estimated_diameter_max")
    public Double getEstimatedDiameterMax() {
        return estimatedDiameterMax;
    }

    @JsonProperty("estimated_diameter_max")
    public void setEstimatedDiameterMax(Double estimatedDiameterMax) {
        this.estimatedDiameterMax = estimatedDiameterMax;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("estimatedDiameterMin", estimatedDiameterMin).append("estimatedDiameterMax", estimatedDiameterMax).toString();
    }

}
