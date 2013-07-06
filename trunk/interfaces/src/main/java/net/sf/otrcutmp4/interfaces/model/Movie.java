package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Movie<COVER extends Cover>
			extends EjbWithId,EjbWithName
{	
	int getYear();
	void setYear(int year);
	
	COVER getCover();
	void setCover(COVER cover);
}