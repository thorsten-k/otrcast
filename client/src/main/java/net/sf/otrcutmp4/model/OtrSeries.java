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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.model.interfaces.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.qualifier.EjbErNode;
import net.sf.otrcutmp4.interfaces.model.Series;

@Entity
@Table(name="Series")
@EjbErNode(name="Series")
public class OtrSeries implements Serializable,EjbWithId,EjbPersistable,EjbWithName,
									Series<OtrSeries,OtrSeason,OtrEpisode>
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String name;
	
	private String code;
			
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="series")
	@OrderBy("nr ASC")
	private List<OtrSeason> seasons;
		
	// >>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<
			
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getCode() {return code;}
	public void setCode(String code) {this.code = code;}
	
	public List<OtrSeason> getSeasons() {if(seasons==null){seasons = new ArrayList<OtrSeason>();} return seasons;}
	public void setSeasons(List<OtrSeason> seasons) {this.seasons = seasons;}
	
	// >>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<
	
	public boolean equals(Object object)
	{
        return (object instanceof OtrSeries) ? id == ((OtrSeries) object).getId() : (object == this);
    }
}