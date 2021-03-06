package de.kisner.otrcast.interfaces.model;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

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