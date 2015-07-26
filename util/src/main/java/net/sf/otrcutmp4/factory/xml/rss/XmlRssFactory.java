package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.model.xml.rss.Channel;
import net.sf.otrcutmp4.model.xml.rss.Rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
