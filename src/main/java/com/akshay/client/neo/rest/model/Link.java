package com.akshay.client.neo.rest.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self"
})
public class Link implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2848059977267482477L;
	@JsonProperty("self")
    protected String self;

    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return "link: "+getSelf();
    }

}
