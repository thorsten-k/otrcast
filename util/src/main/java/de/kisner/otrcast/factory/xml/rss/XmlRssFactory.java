package de.kisner.otrcast.factory.xml.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kisner.otrcast.model.xml.rss.Channel;
import de.kisner.otrcast.model.xml.rss.Rss;

public class XmlRssFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlRssFactory.class);
	
	public static Rss build(Channel channel)
	{
		Rss xml = build();
		xml.setChannel(channel);
		return xml;
	}
	
	public static Rss build()
	{
		Rss xml = new Rss();
		xml.setVersion("2.0");
		return xml;
	}
}
