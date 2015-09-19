package de.kisner.otrcast.model.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class JsonOtrBox implements Serializable
{
	public static final long serialVersionUID=1;

	@JsonProperty("identifier")
	private List<JsonOtrtIdentifier> identifier;
	public List<JsonOtrtIdentifier> getIdentifier() {return identifier;}
	public void setIdentifier(List<JsonOtrtIdentifier> identifier) {this.identifier = identifier;}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}