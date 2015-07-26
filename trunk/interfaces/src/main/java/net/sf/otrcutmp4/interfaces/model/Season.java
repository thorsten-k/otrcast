package net.sf.otrcutmp4.interfaces.model;

import java.util.List;

import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Season<SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
						SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
						EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
						IMAGE extends Image,STORAGE extends Storage>
			extends EjbWithId,EjbWithNr,EjbWithName
{	
	SERIES getSeries();
	void setSeries(SERIES series);
	
	IMAGE getCover();
	void setCover(IMAGE image);
	
	List<EPISODE> getEpisodes();
	void setEpisodes(List<EPISODE> episodes);
}