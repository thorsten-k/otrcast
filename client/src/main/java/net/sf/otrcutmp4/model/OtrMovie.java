package net.sf.otrcutmp4.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.otrcutmp4.interfaces.model.Movie;

@Entity
public class OtrMovie implements Serializable,EjbPersistable,Movie<OtrImage,OtrStorage>
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private int year;

	private String name;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private OtrImage cover;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private OtrStorage storage;
			
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
	
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public int getYear() {return year;}
	@Override public void setYear(int year) {this.year = year;}
	
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	@Override public OtrImage getCover() {return cover;}
	@Override public void setCover(OtrImage cover) {this.cover = cover;}
	
	@Override public OtrStorage getStorage() {return storage;}
	@Override public void setStorage(OtrStorage storage) {this.storage = storage;}
	
	// >>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<
	
	public boolean equals(Object object)
	{
        return (object instanceof OtrMovie)
             ? id == ((OtrMovie) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		sb.append(" ").append(year);
		sb.append(" ").append(name);
		return sb.toString();
	}
}