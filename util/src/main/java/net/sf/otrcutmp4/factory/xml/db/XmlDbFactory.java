package net.sf.otrcutmp4.factory.xml.db;

import net.sf.otrcutmp4.model.xml.db.Db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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