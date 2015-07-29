package de.kisner.otrcast.interfaces.model;

import net.sf.ahtutils.interfaces.model.with.EjbWithFileType;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface Image extends EjbWithId,EjbWithFileType
{
	byte[] getData();
	void setData(byte[] data);
	
	public String getUrl();
    public void setUrl(String url);
}