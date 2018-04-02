package de.kisner.otrcast.factory.xml.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.series.Videos;

public class XmlVideosFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlVideosFactory.class);
	
	public static Videos build()
	{
		Videos xml = new Videos();
		return xml;
	}
}