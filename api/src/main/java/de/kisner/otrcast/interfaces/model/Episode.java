package de.kisner.otrcast.interfaces.model;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.with.EjbWithNr;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface Episode<SEASON extends Season<?,SEASON,?,?,?>>
					extends Serializable,EjbPersistable,EjbWithId,EjbWithNr,EjbWithName
{	
	SEASON getSeason();
	void setSeason(SEASON season);
	
//	STORAGE getStorage();
//	void setStorage(STORAGE storage);
}