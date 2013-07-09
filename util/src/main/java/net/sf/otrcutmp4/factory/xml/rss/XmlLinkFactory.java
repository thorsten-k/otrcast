package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.model.xml.rss.Link;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
