package net.sf.otrcutmp4.factory.xml.tvdb;

import net.sf.otrcutmp4.model.xml.tvdb.Sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
