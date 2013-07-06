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
				
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
		
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public OtrSeason getSeason() {return season;}
	public void setSeason(OtrSeason season) {this.season = season;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public int getNr() {return nr;}
	public void setNr(int nr) {this.nr = nr;}
	
	// >>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(" ").append(nr);
		sb.append(" ").append(name);
		return sb.toString();
	}
	
	public boolean equals(Object object)
	{
        return (object instanceof OtrEpisode)
             ? id == ((OtrEpisode) object).getId()
             : (object == this);
    }
}