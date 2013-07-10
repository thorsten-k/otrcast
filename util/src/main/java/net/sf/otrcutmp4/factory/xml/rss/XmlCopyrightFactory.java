package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.model.xml.rss.Copyright;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
