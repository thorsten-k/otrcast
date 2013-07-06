package net.sf.otrcutmp4.interfaces.model;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithType;

public interface Cover extends EjbWithId,EjbWithType
{
	byte[] getData();
	void setData(byte[] data);
}