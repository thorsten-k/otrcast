package de.kisner.otrcast.interfaces.model;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Season<SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE>,
						SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
						EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
						IMAGE extends Image,STORAGE extends Storage>
			extends Serializable,EjbWithId,
					EjbPersistable,
					EjbWithNr,EjbWithName
{	
	SERIES getSeries();
	void setSeries(SERIES series);
	
	IMAGE getCover();
	void setCover(IMAGE image);
	
	List<EPISODE> getEpisodes();
	void setEpisodes(List<EPISODE> episodes);
}