package de.kisner.otrcast.model.ejb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import de.kisner.otrcast.interfaces.model.Storage;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;

@Entity
public class OtrStorage implements Serializable,Storage,
									EjbRemoveable
{
	private static final long serialVersionUID = 1L;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public void setId(long id) {this.id = id;}
	@Override public long getId() {return id;}
	
	@NotNull
	private String name;
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	private long size;
	@Override public long getSize() {return size;}
	@Override public void setSize(long size) {this.size = size;}

	private String hash;
	@Override public String getHash() {return hash;}
	@Override public void setHash(String hash) {this.hash = hash;}
	
	private Date record;
	@Override public Date getRecord() {return record;}
	@Override public void setRecord(Date record) {this.record = record;}

	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append("[").append(id).append("]");
			sb.append(" ").append(size);
			sb.append(" ").append(record);
		return sb.toString();
	}
}