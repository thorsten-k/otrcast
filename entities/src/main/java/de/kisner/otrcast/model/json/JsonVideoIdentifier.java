package de.kisner.otrcast.model.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonVideoIdentifier implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("Type")
	private String typeMain;
	public String getTypeMain() {return typeMain;}
	public void setTypeMain(String typeMain) {this.typeMain = typeMain;}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
}