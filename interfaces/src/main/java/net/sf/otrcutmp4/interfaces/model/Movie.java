package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Movie<IMAGE extends Image,STORAGE extends Storage>
					extends EjbWithId,EjbWithName
{	
	int getYear();
	void setYear(int year);
	
	IMAGE getCover();
	void setCover(IMAGE cover);
	
	STORAGE getStorage();
	void setStorage(STORAGE storage);
}