package net.sf.otrcutmp4.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;
import net.sf.otrcutmp4.interfaces.model.Episode;

@Entity
public class OtrEpisode implements Serializable,EjbWithId,EjbPersistable,EjbWithNr,
									Episode<OtrSeries,OtrSeason,OtrEpisode,OtrCover>
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@NotNull
	@ManyToOne
	private OtrSeason season;
		
	@NotNull
	private String name;
	
	private int nr;
	
	private long size;
				
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
		
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@Override public OtrSeason getSeason() {return season;}
	@Override public void setSeason(OtrSeason season) {this.season = season;}
	
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	@Override public int getNr() {return nr;}
	@Override public void setNr(int nr) {this.nr = nr;}
	
	@Override public long getSize() {return size;}
	@Override public void setSize(long size) {this.size = size;}
	
	// >>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<
	
	public boolean equals(Object object)
	{
        return (object instanceof OtrEpisode)
             ? id == ((OtrEpisode) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		sb.append(" ").append(nr);
		sb.append(" ").append(name);
		return sb.toString();
	}
}