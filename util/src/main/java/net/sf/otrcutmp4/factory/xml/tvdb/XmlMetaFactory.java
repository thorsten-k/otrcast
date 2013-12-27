package net.sf.otrcutmp4.factory.xml.tvdb;

import net.sf.otrcutmp4.model.xml.tvdb.Meta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMetaFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlMetaFactory.class);
	
	public static Meta build(String id)
	{
		return build(new Long(id));
	}

    public static Meta build(long id)
    {
        Meta xml = new Meta();
        xml.setId(id);

        return xml;
    }
}
