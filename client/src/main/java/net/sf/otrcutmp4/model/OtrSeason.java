package net.sf.otrcutmp4.model;

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

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.otrcutmp4.interfaces.model.Season;

@Entity
public class OtrSeason implements Serializable,EjbPersistable,
									Season<OtrSeries,OtrSeason,OtrEpisode,OtrCover,OtrStorage>
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@ManyToOne
	private OtrSeries series;
	
	private long nr;
	@Override public long getNr() {return nr;}
	@Override public void setNr(long nr) {this.nr = nr;}
	
	private String name;
	
	private boolean showNr;
	
	private boolean showName;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private OtrCover cover;

	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="season")
	@OrderBy("nr ASC")
	private List<OtrEpisode> episodes;
			
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
		
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@Override public OtrSeries getSeries() {return series;}
	@Override public void setSeries(OtrSeries series) {this.series = series;}
	
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	public boolean isShowNr() {return showNr;}
	public void setShowNr(boolean showNr) {this.showNr = showNr;}
	
	public boolean isShowName() {return showName;}
	public void setShowName(boolean showName) {this.showName = showName;}
	
	@Override public OtrCover getCover() {return cover;}
	@Override public void setCover(OtrCover cover) {this.cover = cover;}
	
	@Override public List<OtrEpisode> getEpisodes() {if(episodes==null){episodes = new ArrayList<OtrEpisode>();} return episodes;}
	@Override public void setEpisodes(List<OtrEpisode> episodes) {this.episodes = episodes;}
	
	// >>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<
	
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