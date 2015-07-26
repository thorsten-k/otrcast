package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Episode<SERIES extends Series<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							SEASON extends Season<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							EPISODE extends Episode<SERIES,SEASON,EPISODE,IMAGE,STORAGE>,
							IMAGE extends Image,STORAGE extends Storage>
					extends EjbWithId,EjbWithNr,EjbWithName
{	
	SEASON getSeason();
	void setSeason(SEASON season);
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
}