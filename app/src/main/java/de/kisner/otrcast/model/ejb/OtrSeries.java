package de.kisner.otrcast.model.ejb;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import de.kisner.otrcast.interfaces.model.Series;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

@Entity
public class OtrSeries implements Serializable,EjbWithId,EjbPersistable,EjbWithName,
									Series<OtrSeries,OtrSeason,OtrEpisode,OtrImage>
{
	public static final long serialVersionUID=1;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}

	@NotNull
	private String name;
	@Override public String getName() {return name;}
	@Override public void setName(String name) {this.name = name;}
	
	private String code;
	@Override public String getCode() {return code;}
	@Override public void setCode(String code) {this.code = code;}
			
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="series")
	@OrderBy("nr ASC")
	private List<OtrSeason> seasons;
	@Override public List<OtrSeason> getSeasons() {if(seasons==null){seasons = new ArrayList<OtrSeason>();} return seasons;}
	@Override public void setSeasons(List<OtrSeason> seasons) {this.seasons = seasons;}
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private OtrImage banner;
	@Override public OtrImage getBanner() {return banner;}
	@Override public void setBanner(OtrImage banner) {this.banner = banner;}
	
	
	public boolean equals(Object object)
	{
        return (object instanceof OtrSeries) ? id == ((OtrSeries) object).getId() : (object == this);
    }
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(id).append("]");
		sb.append(" ").append(name);
		return sb.toString();
	}
}