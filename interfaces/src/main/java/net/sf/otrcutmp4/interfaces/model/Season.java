package net.sf.otrcutmp4.interfaces.model;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;

public interface Season<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
			extends EjbWithId,EjbWithNr,EjbWithName
{	
	SERIES getSeries();
	void setSeries(SERIES series);
	
	List<EPISODE> getEpisodes();
	void setEpisodes(List<EPISODE> episodes);
}