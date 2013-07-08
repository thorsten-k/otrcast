package net.sf.otrcutmp4.factory.xml.rss;

import net.sf.otrcutmp4.model.xml.rss.Title;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTitleFactory
{	
	final static Logger logger = LoggerFactory.getLogger(XmlTitleFactory.class);
	
	public static Title build(String title)
	{
		Title xml = new Title();
		xml.setValue(title);
		return xml;
	}
}
