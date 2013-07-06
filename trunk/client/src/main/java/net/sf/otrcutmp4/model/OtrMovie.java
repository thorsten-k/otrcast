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
public class OtrMovie implements Serializable,EjbPersistable,Movie<OtrCover>
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private int year;
	
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private OtrCover cover;
			
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
		
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public int getYear() {return year;}
	public void setYear(int year) {this.year = year;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public OtrCover getCover() {return cover;}
	public void setCover(OtrCover cover) {this.cover = cover;}
	
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