package net.sf.otrcutmp4.interfaces.model;

import java.util.List;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;

public interface Season<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
			extends EjbWithId,EjbWithNr,EjbWithName
{	
	SERIES getSeries();
	void setSeries(SERIES series);
	
	COVER getCover();
	void setCover(COVER cover);
	
	List<EPISODE> getEpisodes();
	void setEpisodes(List<EPISODE> episodes);
}