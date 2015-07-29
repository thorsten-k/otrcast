package de.kisner.otrcast.interfaces.model;

import net.sf.ahtutils.interfaces.model.with.EjbWithHash;
import net.sf.ahtutils.interfaces.model.with.EjbWithSize;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;
import net.sf.ahtutils.model.interfaces.with.EjbWithRecord;

public interface Storage extends EjbWithId,EjbWithName,EjbWithSize,EjbWithHash,EjbWithRecord
{
	
}