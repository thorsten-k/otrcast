package de.kisner.otrcast.factory.xml.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.db.Db;

public class XmlDbFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlDbFactory.class);
	
	public static Db build(String source, String id)
	{	
		Db xml = new Db();
		xml.setSource(source);
		xml.setId(id);
		return xml;
	}
}