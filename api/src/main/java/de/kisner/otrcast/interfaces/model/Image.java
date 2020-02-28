package de.kisner.otrcast.interfaces.model;

import org.jeesl.interfaces.model.with.primitive.number.EjbWithId;
import org.jeesl.interfaces.model.with.primitive.text.EjbWithFileType;

public interface Image extends EjbWithId,EjbWithFileType
{
	byte[] getData();
	void setData(byte[] data);
	
	public String getUrl();
    public void setUrl(String url);
}