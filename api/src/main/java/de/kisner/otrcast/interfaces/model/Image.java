package de.kisner.otrcast.interfaces.model;

import org.jeesl.interfaces.model.with.text.EjbWithFileType;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface Image extends EjbWithId,EjbWithFileType
{
	byte[] getData();
	void setData(byte[] data);
	
	public String getUrl();
    public void setUrl(String url);
}