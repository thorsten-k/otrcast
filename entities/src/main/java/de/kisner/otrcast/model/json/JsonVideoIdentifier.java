package de.kisner.otrcast.model.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"scheme", "type", "id"})
public class JsonVideoIdentifier implements Serializable
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

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}