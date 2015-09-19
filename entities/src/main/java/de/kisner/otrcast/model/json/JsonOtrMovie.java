package de.kisner.otrcast.model.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","year"})
public class JsonOtrMovie implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("id")
	private long id;
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	@JsonProperty("year")
	private int year;
	public int getYear() {return year;}
	public void setYear(int year) {this.year = year;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}