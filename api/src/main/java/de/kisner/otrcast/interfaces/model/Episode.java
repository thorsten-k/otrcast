package de.kisner.otrcast.interfaces.model;

import java.io.Serializable;

import org.jeesl.interfaces.model.marker.jpa.EjbPersistable;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithNr;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface Episode<SEASON extends Season<?,SEASON,?,?,?>>
					extends Serializable,EjbPersistable,EjbWithId,EjbWithNr,EjbWithName
{	
	SEASON getSeason();
	void setSeason(SEASON season);
	
//	STORAGE getStorage();
//	void setStorage(STORAGE storage);
}