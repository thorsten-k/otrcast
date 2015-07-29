package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Copyright;

public class XmlCopyrightFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlCopyrightFactory.class);
	
	public static Copyright build(String copyright)
	{
		Copyright xml = new Copyright();
		xml.setValue(copyright);
		return xml;
	}
}
