package de.kisner.otrcast.interfaces.model;

import org.jeesl.interfaces.model.with.number.EjbWithSize;
import org.jeesl.interfaces.model.with.text.EjbWithHash;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface Storage extends EjbWithId,EjbWithName,EjbWithSize,EjbWithHash,EjbWithRecord
{
	
}