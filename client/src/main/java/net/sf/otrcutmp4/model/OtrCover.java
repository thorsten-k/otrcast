package net.sf.otrcutmp4.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import net.sf.otrcutmp4.interfaces.model.Cover;

@Entity
public class OtrCover implements Serializable,Cover
{
	private static final long serialVersionUID = 1L;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String type;

	@Lob
	private byte[] data;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Override public void setId(long id) {this.id = id;}
	@Override public long getId() {return id;}
	
	@Override public String getType() {return type;}
	@Override public void setType(String type) {this.type = type;}
	
	@Override public byte[] getData() {return data;}
	@Override public void setData(byte[] data) {this.data = data;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append("[").append(id).append("]");
			sb.append(" type=").append(type);
			sb.append(" size=").append(data.length);
		return sb.toString();
	}
}