package de.kisner.otrcast.factory.xml.tvdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.tvdb.Sync;

public class XmlSyncFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlSyncFactory.class);
	
	public static Sync build(String id)
	{
		return build(new Long(id));
	}

    public static Sync build(long id)
    {
        Sync xml = new Sync();
        xml.setId(id);

        return xml;
    }
}
