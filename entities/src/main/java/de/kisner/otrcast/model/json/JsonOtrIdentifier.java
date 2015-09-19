package de.kisner.otrcast.model.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"scheme", "type", "id"})
@JsonInclude(Include.NON_NULL)
public class JsonOtrIdentifier implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("scheme")
	private String scheme;
	public String getScheme() {return scheme;}
	public void setScheme(String scheme) {this.scheme = scheme;}

	@JsonProperty("type")
	private String typeMain;
	public String getType() {return typeMain;}
	public void setType(String typeMain) {this.typeMain = typeMain;}
	
	@JsonProperty("id")
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@JsonProperty("movie")
	private JsonOtrMovie movie;
	public JsonOtrMovie getMovie() {return movie;}
	public void setMovie(JsonOtrMovie movie) {this.movie = movie;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}