package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Link;

public class XmlLinkFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlLinkFactory.class);
	
	public static Link build(String link)
	{
		Link xml = new Link();
		xml.setValue(link);
		return xml;
	}
}
