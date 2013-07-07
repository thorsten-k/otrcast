package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.interfaces.model.with.EjbWithSize;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Movie<COVER extends Cover,STORAGE extends Storage>
					extends EjbWithId,EjbWithName,EjbWithSize
{	
	int getYear();
	void setYear(int year);
	
	COVER getCover();
	void setCover(COVER cover);
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
}