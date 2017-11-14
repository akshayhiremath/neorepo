/**
 * 
 */
package com.akshay.client.neo.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author AKSHAYH
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"close_approach_date",
"epoch_date_close_approach",
"relative_velocity",
"miss_distance",
"orbiting_body"
})
public class CloseApproachDatum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3275214535523307577L;
	@JsonProperty("close_approach_date")
	private String close_approach_date;
	@JsonProperty("epoch_date_close_approach")
	private Long epoch_date_close_approach;
	@JsonProperty("relative_velocity")
	private RelativeVelocity relative_velocity;
	@JsonProperty("miss_distance")
	private MissDistance miss_distance;
	@JsonProperty("orbiting_body")
	private String orbiting_body;

	public String getClose_approach_date() {
		return close_approach_date;
	}

	public void setClose_approach_date(String close_approach_date) {
		this.close_approach_date = close_approach_date;
	}

	public Long getEpoch_date_close_approach() {
		return epoch_date_close_approach;
	}

	public void setEpoch_date_close_approach(Long epoch_date_close_approach) {
		this.epoch_date_close_approach = epoch_date_close_approach;
	}

	public RelativeVelocity getRelative_velocity() {
		return relative_velocity;
	}

	public void setRelative_velocity(RelativeVelocity relative_velocity) {
		this.relative_velocity = relative_velocity;
	}

	public MissDistance getMiss_distance() {
		return miss_distance;
	}

	public void setMiss_distance(MissDistance miss_distance) {
		this.miss_distance = miss_distance;
	}

	public String getOrbiting_body() {
		return orbiting_body;
	}

	public void setOrbiting_body(String orbiting_body) {
		this.orbiting_body = orbiting_body;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("closeApproachDate", close_approach_date)
				.append("epochDateCloseApproach", epoch_date_close_approach).append("relativeVelocity", relative_velocity)
				.append("missDistance", miss_distance).append("orbitingBody", orbiting_body).toString();
	}
}
