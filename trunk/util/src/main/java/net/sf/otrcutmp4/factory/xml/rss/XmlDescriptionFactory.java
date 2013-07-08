package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.model.xml.rss.Description;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlDescriptionFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlDescriptionFactory.class);
	
	public static Description build(String description)
	{
		Description xml = new Description();
		xml.setValue(description);
		return xml;
	}
}
