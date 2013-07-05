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
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;
import net.sf.otrcutmp4.interfaces.model.Season;

@Entity
public class OtrSeason implements Serializable,EjbWithId,EjbPersistable,EjbWithNr,
									Season<OtrSeries,OtrSeason,OtrEpisode>
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@ManyToOne
	private OtrSeries series;
	
	private int nr;
	
	private String name;
	
	private boolean showNr;
	
	private boolean showName;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="season")
	@OrderBy("nr ASC")
	private List<OtrEpisode> episodes;
			
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
		
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public OtrSeries getSeries() {return series;}
	public void setSeries(OtrSeries series) {this.series = series;}
	
	public int getNr() {return nr;}
	public void setNr(int nr) {this.nr = nr;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public boolean isShowNr() {return showNr;}
	public void setShowNr(boolean showNr) {this.showNr = showNr;}
	
	public boolean isShowName() {return showName;}
	public void setShowName(boolean showName) {this.showName = showName;}
	
	public List<OtrEpisode> getEpisodes() {if(episodes==null){episodes = new ArrayList<OtrEpisode>();} return episodes;}
	public void setEpisodes(List<OtrEpisode> episodes) {this.episodes = episodes;}
	
	// >>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<
	
	public boolean equals(Object object)
	{
        return (object instanceof OtrSeason)
             ? id == ((OtrSeason) object).getId()
             : (object == this);
    }
}