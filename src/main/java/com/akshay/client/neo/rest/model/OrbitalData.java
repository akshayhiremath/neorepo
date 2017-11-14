package com.akshay.client.neo.rest.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "orbit_id",
    "orbit_determination_date",
    "orbit_uncertainty",
    "minimum_orbit_intersection",
    "jupiter_tisserand_invariant",
    "epoch_osculation",
    "eccentricity",
    "semi_major_axis",
    "inclination",
    "ascending_node_longitude",
    "orbital_period",
    "perihelion_distance",
    "perihelion_argument",
    "aphelion_distance",
    "perihelion_time",
    "mean_anomaly",
    "mean_motion",
    "equinox"
})
public class OrbitalData implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3241013327487173552L;
	@JsonProperty("orbit_id")
    private String orbitId;
    @JsonProperty("orbit_determination_date")
    private String orbitDeterminationDate;
    @JsonProperty("orbit_uncertainty")
    private String orbitUncertainty;
    @JsonProperty("minimum_orbit_intersection")
    private String minimumOrbitIntersection;
    @JsonProperty("jupiter_tisserand_invariant")
    private String jupiterTisserandInvariant;
    @JsonProperty("epoch_osculation")
    private String epochOsculation;
    @JsonProperty("eccentricity")
    private String eccentricity;
    @JsonProperty("semi_major_axis")
    private String semiMajorAxis;
    @JsonProperty("inclination")
    private String inclination;
    @JsonProperty("ascending_node_longitude")
    private String ascendingNodeLongitude;
    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    @JsonProperty("perihelion_distance")
    private String perihelionDistance;
    @JsonProperty("perihelion_argument")
    private String perihelionArgument;
    @JsonProperty("aphelion_distance")
    private String aphelionDistance;
    @JsonProperty("perihelion_time")
    private String perihelionTime;
    @JsonProperty("mean_anomaly")
    private String meanAnomaly;
    @JsonProperty("mean_motion")
    private String meanMotion;
    @JsonProperty("equinox")
    private String equinox;

    
    public OrbitalData() {

	}
    
	@JsonCreator
    public OrbitalData(@JsonProperty("orbit_id") String orbitId, @JsonProperty("orbit_determination_date") String orbitDeterminationDate, @JsonProperty("orbit_uncertainty") String orbitUncertainty,
    		@JsonProperty("minimum_orbit_intersection") String minimumOrbitIntersection,  @JsonProperty("jupiter_tisserand_invariant") String jupiterTisserandInvariant, @JsonProperty("epoch_osculation") String epochOsculation,
    		 @JsonProperty("eccentricity") String eccentricity, @JsonProperty("semi_major_axis") String semiMajorAxis,  @JsonProperty("inclination") String inclination, @JsonProperty("ascending_node_longitude") String ascendingNodeLongitude,
    		 @JsonProperty("orbital_period") String orbitalPeriod, @JsonProperty("perihelion_distance") String perihelionDistance, @JsonProperty("perihelion_argument") String perihelionArgument, @JsonProperty("aphelion_distance") String aphelionDistance,
    		  @JsonProperty("perihelion_time") String perihelionTime,  @JsonProperty("mean_anomaly") String meanAnomaly, @JsonProperty("mean_motion") String meanMotion,  @JsonProperty("equinox") String equinox) {
		this.orbitId = orbitId;
		this.orbitDeterminationDate = orbitDeterminationDate;
		this.orbitUncertainty = orbitUncertainty;
		this.minimumOrbitIntersection = minimumOrbitIntersection;
		this.jupiterTisserandInvariant = jupiterTisserandInvariant;
		this.epochOsculation = epochOsculation;
		this.eccentricity = eccentricity;
		this.semiMajorAxis = semiMajorAxis;
		this.inclination = inclination;
		this.ascendingNodeLongitude = ascendingNodeLongitude;
		this.orbitalPeriod = orbitalPeriod;
		this.perihelionDistance = perihelionDistance;
		this.perihelionArgument = perihelionArgument;
		this.aphelionDistance = aphelionDistance;
		this.perihelionTime = perihelionTime;
		this.meanAnomaly = meanAnomaly;
		this.meanMotion = meanMotion;
		this.equinox = equinox;
	}
   
    @JsonProperty("orbit_id")
    public String getOrbitId() {
        return orbitId;
    }

    @JsonProperty("orbit_id")
    public void setOrbitId(String orbitId) {
        this.orbitId = orbitId;
    }

    @JsonProperty("orbit_determination_date")
    public String getOrbitDeterminationDate() {
        return orbitDeterminationDate;
    }

    @JsonProperty("orbit_determination_date")
    public void setOrbitDeterminationDate(String orbitDeterminationDate) {
        this.orbitDeterminationDate = orbitDeterminationDate;
    }

    @JsonProperty("orbit_uncertainty")
    public String getOrbitUncertainty() {
        return orbitUncertainty;
    }

    @JsonProperty("orbit_uncertainty")
    public void setOrbitUncertainty(String orbitUncertainty) {
        this.orbitUncertainty = orbitUncertainty;
    }

    @JsonProperty("minimum_orbit_intersection")
    public String getMinimumOrbitIntersection() {
        return minimumOrbitIntersection;
    }

    @JsonProperty("minimum_orbit_intersection")
    public void setMinimumOrbitIntersection(String minimumOrbitIntersection) {
        this.minimumOrbitIntersection = minimumOrbitIntersection;
    }

    @JsonProperty("jupiter_tisserand_invariant")
    public String getJupiterTisserandInvariant() {
        return jupiterTisserandInvariant;
    }

    @JsonProperty("jupiter_tisserand_invariant")
    public void setJupiterTisserandInvariant(String jupiterTisserandInvariant) {
        this.jupiterTisserandInvariant = jupiterTisserandInvariant;
    }

    @JsonProperty("epoch_osculation")
    public String getEpochOsculation() {
        return epochOsculation;
    }

    @JsonProperty("epoch_osculation")
    public void setEpochOsculation(String epochOsculation) {
        this.epochOsculation = epochOsculation;
    }

    @JsonProperty("eccentricity")
    public String getEccentricity() {
        return eccentricity;
    }

    @JsonProperty("eccentricity")
    public void setEccentricity(String eccentricity) {
        this.eccentricity = eccentricity;
    }

    @JsonProperty("semi_major_axis")
    public String getSemiMajorAxis() {
        return semiMajorAxis;
    }

    @JsonProperty("semi_major_axis")
    public void setSemiMajorAxis(String semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    @JsonProperty("inclination")
    public String getInclination() {
        return inclination;
    }

    @JsonProperty("inclination")
    public void setInclination(String inclination) {
        this.inclination = inclination;
    }

    @JsonProperty("ascending_node_longitude")
    public String getAscendingNodeLongitude() {
        return ascendingNodeLongitude;
    }

    @JsonProperty("ascending_node_longitude")
    public void setAscendingNodeLongitude(String ascendingNodeLongitude) {
        this.ascendingNodeLongitude = ascendingNodeLongitude;
    }

    @JsonProperty("orbital_period")
    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }

    @JsonProperty("orbital_period")
    public void setOrbitalPeriod(String orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    @JsonProperty("perihelion_distance")
    public String getPerihelionDistance() {
        return perihelionDistance;
    }

    @JsonProperty("perihelion_distance")
    public void setPerihelionDistance(String perihelionDistance) {
        this.perihelionDistance = perihelionDistance;
    }

    @JsonProperty("perihelion_argument")
    public String getPerihelionArgument() {
        return perihelionArgument;
    }

    @JsonProperty("perihelion_argument")
    public void setPerihelionArgument(String perihelionArgument) {
        this.perihelionArgument = perihelionArgument;
    }

    @JsonProperty("aphelion_distance")
    public String getAphelionDistance() {
        return aphelionDistance;
    }

    @JsonProperty("aphelion_distance")
    public void setAphelionDistance(String aphelionDistance) {
        this.aphelionDistance = aphelionDistance;
    }

    @JsonProperty("perihelion_time")
    public String getPerihelionTime() {
        return perihelionTime;
    }

    @JsonProperty("perihelion_time")
    public void setPerihelionTime(String perihelionTime) {
        this.perihelionTime = perihelionTime;
    }

    @JsonProperty("mean_anomaly")
    public String getMeanAnomaly() {
        return meanAnomaly;
    }

    @JsonProperty("mean_anomaly")
    public void setMeanAnomaly(String meanAnomaly) {
        this.meanAnomaly = meanAnomaly;
    }

    @JsonProperty("mean_motion")
    public String getMeanMotion() {
        return meanMotion;
    }

    @JsonProperty("mean_motion")
    public void setMeanMotion(String meanMotion) {
        this.meanMotion = meanMotion;
    }

    @JsonProperty("equinox")
    public String getEquinox() {
        return equinox;
    }

    @JsonProperty("equinox")
    public void setEquinox(String equinox) {
        this.equinox = equinox;
    }

    @Override
    public String toString() {
//		return new ToStringBuilder(this).append("orbitId", orbitId).append("\n")
//				.append("orbitDeterminationDate", orbitDeterminationDate).append("\n")
//				.append("orbitUncertainty", orbitUncertainty).append("\n")
//				.append("minimumOrbitIntersection", minimumOrbitIntersection).append("\n")
//				.append("jupiterTisserandInvariant", jupiterTisserandInvariant).append("\n")
//				.append("epochOsculation", epochOsculation).append("\n")
//				.append("eccentricity", eccentricity).append("\n")
//				.append("semiMajorAxis", semiMajorAxis).append("\n")
//				.append("inclination", inclination).append("\n")
//				.append("ascendingNodeLongitude", ascendingNodeLongitude)
//				.append("orbitalPeriod", orbitalPeriod).append("\n")
//				.append("perihelionDistance", perihelionDistance).append("\n")
//				.append("perihelionArgument", perihelionArgument).append("\n")
//				.append("aphelionDistance", aphelionDistance).append("\n")
//				.append("perihelionTime", perihelionTime).append("\n")
//				.append("meanAnomaly", meanAnomaly).append("\n")
//				.append("meanMotion", meanMotion).append("\n")
//				.append("equinox", equinox).append("\n")
//				.toString();
    	
    	return "		orbitId: "+orbitId+"\n"+
				"		orbitDeterminationDate: "+orbitDeterminationDate+"\n"+
				"		orbitUncertainty: "+orbitUncertainty+"\n"+
				"		minimumOrbitIntersection: "+minimumOrbitIntersection+"\n"+
				"		jupiterTisserandInvariant: "+jupiterTisserandInvariant+"\n"+
				"		epochOsculation: "+epochOsculation+"\n"+
				"		eccentricity: "+eccentricity+"\n"+
				"		semiMajorAxis: "+semiMajorAxis+"\n"+
				"		inclination: "+inclination+"\n"+
				"		ascendingNodeLongitude: "+ascendingNodeLongitude+"\n"+
				"		orbitalPeriod: "+orbitalPeriod+"\n"+
				"		perihelionDistance: "+perihelionDistance+"\n"+
				"		perihelionArgument: "+perihelionArgument+"\n"+
				"		aphelionDistance: "+aphelionDistance+"\n"+
				"		perihelionTime: "+perihelionTime+"\n"+
				"		meanAnomaly: "+meanAnomaly+"\n"+
				"		meanMotion: "+meanMotion+"\n"+
				"		equinox: "+equinox+"\n";
    }

}
