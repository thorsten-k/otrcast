package de.kisner.otrcast.factory.xml.series;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.Video;

public class XmlVideoFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlVideoFactory.class);
	
	public static Video build()
	{
		Video xml = new Video();
		return xml;
	}
}
