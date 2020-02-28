package de.kisner.otrcast.interfaces.model;

import org.jeesl.interfaces.model.with.primitive.date.EjbWithRecord;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.number.EjbWithSize;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithHash;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithName;

public interface Storage extends EjbWithId,EjbWithName,EjbWithSize,EjbWithHash,EjbWithRecord
{
	
}