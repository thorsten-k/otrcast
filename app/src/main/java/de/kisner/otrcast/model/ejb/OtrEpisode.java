package de.kisner.otrcast.model.ejb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import de.kisner.otrcast.interfaces.model.Episode;

@Entity
public class OtrEpisode implements Episode<OtrSeason>
{
	public static final long serialVersionUID=1;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@NotNull @ManyToOne
	private OtrSeason season;
	@Override public OtrSeason getSeason() {return season;}
	@Override public void setSeason(OtrSeason season) {this.season = season;}
		
	@NotNull
	private String name;
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	private long nr;
	@Override public long getNr() {return nr;}
	@Override public void setNr(long nr) {this.nr = nr;}
				
	
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