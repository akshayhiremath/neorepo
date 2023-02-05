package com.akshay.client.neo.rest.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "next",
    "prev",
    "self"
})
public class Links extends Link implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1446154749211410698L;
	@JsonProperty("next")
    private String next;
    @JsonProperty("prev")
    private String prev;

    @JsonProperty("next")
    public String getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(String next) {
        this.next = next;
    }

    @JsonProperty("prev")
    public String getPrev() {
        return prev;
    }

    @JsonProperty("prev")
    public void setPrev(String prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("next", next).append("prev", prev).append("self", super.self).toString();
    }

}
