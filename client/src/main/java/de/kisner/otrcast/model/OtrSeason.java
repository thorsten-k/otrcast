package de.kisner.otrcast.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import de.kisner.otrcast.interfaces.model.Season;
import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;

@Entity
public class OtrSeason implements Serializable,EjbPersistable,
									Season<OtrSeries,OtrSeason,OtrEpisode,OtrImage,OtrStorage>
{
	public static final long serialVersionUID=1;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull
	@ManyToOne
	private OtrSeries series;
	@Override public OtrSeries getSeries() {return series;}
	@Override public void setSeries(OtrSeries series) {this.series = series;}
	
	private long nr;
	@Override public long getNr() {return nr;}
	@Override public void setNr(long nr) {this.nr = nr;}
	
	private String name;
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	private boolean showNr;
	public boolean isShowNr() {return showNr;}
	public void setShowNr(boolean showNr) {this.showNr = showNr;}
	
	private boolean showName;
	public boolean isShowName() {return showName;}
	public void setShowName(boolean showName) {this.showName = showName;}
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private OtrImage cover;
	@Override public OtrImage getCover() {return cover;}
	@Override public void setCover(OtrImage cover) {this.cover = cover;}

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="season")
	@OrderBy("nr ASC")
	private List<OtrEpisode> episodes;	
	@Override public List<OtrEpisode> getEpisodes() {if(episodes==null){episodes = new ArrayList<OtrEpisode>();} return episodes;}
	@Override public void setEpisodes(List<OtrEpisode> episodes) {this.episodes = episodes;}
	
	
	public boolean equals(Object object)
	{
        return (object instanceof OtrSeason)
             ? id == ((OtrSeason) object).getId()
             : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		sb.append(" ").append(nr);
		return sb.toString();
	}
}