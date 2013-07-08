package net.sf.otrcutmp4.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import net.sf.otrcutmp4.interfaces.model.Storage;

@Entity
public class OtrStorage implements Serializable,Storage
{
	private static final long serialVersionUID = 1L;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String name;
	
	private long size;

	private String hash;
	
	private Date record;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Override public void setId(long id) {this.id = id;}
	@Override public long getId() {return id;}
	
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	@Override public long getSize() {return size;}
	@Override public void setSize(long size) {this.size = size;}
	
	@Override public String getHash() {return hash;}
	@Override public void setHash(String hash) {this.hash = hash;}
	
	@Override public Date getRecord() {return record;}
	@Override public void setRecord(Date record) {this.record = record;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}