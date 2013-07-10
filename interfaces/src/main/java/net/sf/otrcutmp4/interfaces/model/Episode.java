package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;

public interface Episode<SERIES extends Series<SERIES,SEASON,EPISODE,COVER,STORAGE>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER,STORAGE>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER,STORAGE>,COVER extends Cover,STORAGE extends Storage>
					extends EjbWithId,EjbWithNr,EjbWithName
{	
	SEASON getSeason();
	void setSeason(SEASON season);
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
}