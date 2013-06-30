package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;

public interface Episode<SERIES extends Series<SERIES,SEASON,EPISODE>, SEASON extends Season<SERIES,SEASON,EPISODE>,EPISODE extends Episode<SERIES,SEASON,EPISODE>>
					extends EjbWithId,EjbWithNr,EjbWithName
{	
	SEASON getSeason();
	void setSeason(SEASON season);
}