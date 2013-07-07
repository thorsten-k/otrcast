package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.interfaces.model.with.EjbWithSize;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithNr;

public interface Episode<SERIES extends Series<SERIES,SEASON,EPISODE,COVER>,SEASON extends Season<SERIES,SEASON,EPISODE,COVER>,EPISODE extends Episode<SERIES,SEASON,EPISODE,COVER>,COVER extends Cover>
					extends EjbWithId,EjbWithNr,EjbWithName,EjbWithSize
{	
	SEASON getSeason();
	void setSeason(SEASON season);
}